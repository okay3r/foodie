package top.okay3r.foodie.controller.center;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.pojo.bo.center.CenterUserBO;
import top.okay3r.foodie.resource.FileUpload;
import top.okay3r.foodie.service.center.CenterUserService;
import top.okay3r.foodie.utils.ApiJsonResult;
import top.okay3r.foodie.utils.CookieUtils;
import top.okay3r.foodie.utils.DateUtil;
import top.okay3r.foodie.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户信息", tags = "用户信息相关接口")
@RestController
@RequestMapping("/userInfo")
public class CenterUserController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

    /**
     * 更新用户信息
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "POST")
    public ApiJsonResult update(@RequestParam("userId") String userId,
                                @RequestBody @Valid CenterUserBO centerUserBO,
                                BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = this.getErrors(bindingResult);
            return ApiJsonResult.errorMap(errorsMap);
        }
        Users users = this.centerUserService.updateUserInfo(userId, centerUserBO);
        setNullProperty(users);
        return ApiJsonResult.ok(users);
    }

    /**
     * 上传用户头像
     */
    @PostMapping("/uploadFace")
    @ApiOperation(value = "上传用户头像", notes = "上传用户头像", httpMethod = "POST")
    public ApiJsonResult update(@RequestParam("userId") String userId,
                                MultipartFile file,
                                HttpServletRequest request,
                                HttpServletResponse response
    ) {

        String fileSpace = fileUpload.getImageUserFaceLocation();
        String uploadFilePrefix = File.separator + userId;
        //开始文件上传
        if (file == null) {
            return ApiJsonResult.errorMsg("文件不能为空！");
        }
        FileOutputStream fileOutputStream = null;
        try {
            String filename = file.getOriginalFilename();
            if (StringUtils.isBlank(filename)) {
                return ApiJsonResult.errorMsg("文件名不能为空！");
            }
            //获取文件后缀名
            String[] split = filename.split("\\.");
            String suffix = split[split.length - 1];
            if (!suffix.equalsIgnoreCase("png") &&
                    !suffix.equalsIgnoreCase("jpg") &&
                    !suffix.equalsIgnoreCase("jpeg")
            ) {
                return ApiJsonResult.errorMsg("文件格式不正确！");
            }

            // 编辑存储到磁盘中的文件名
            String newFileName = "face-" + userId + "." + suffix;
            String finalFaceFilePath = fileSpace + uploadFilePrefix + File.separator + newFileName;
            //提供给web服务访问的地址
            uploadFilePrefix += ("/" + newFileName);

            File targetFile = new File(finalFaceFilePath);
            if (targetFile.getParentFile() != null) {
                //如果没有改文件夹，则创建
                targetFile.getParentFile().mkdirs();
            }

            //写到文件
            fileOutputStream = new FileOutputStream(targetFile);
            InputStream inputStream = file.getInputStream();
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //更新到数据库
        // 获取图片服务地址
        String imageServerUrl = fileUpload.getImageServerUrl();

        // 由于浏览器可能存在缓存的情况，所以在这里，我们需要加上时间戳来保证更新后的图片可以及时刷新
        String finalUserFaceUrl = imageServerUrl + uploadFilePrefix
                + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);

        // 更新用户头像到数据库
        Users userResult = centerUserService.updateUserFace(userId, finalUserFaceUrl);

        setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);

        // TODO 后续要改，增加令牌token，会整合进redis，分布式会话

        return ApiJsonResult.ok();
    }

    /**
     * 用于获取错误信息
     */
    private Map<String, String> getErrors(BindingResult bindingResult) {
        List<FieldError> errorList = bindingResult.getFieldErrors();
        Map<String, String> map = new HashMap<>();
        for (FieldError error : errorList) {
            String errorField = error.getField();
            String errorMsg = error.getDefaultMessage();
            map.put(errorField, errorMsg);
        }
        return map;
    }

    private void setNullProperty(Users userRes) {
        userRes.setPassword(null);
        userRes.setCreatedTime(null);
        userRes.setUpdatedTime(null);
    }
}
