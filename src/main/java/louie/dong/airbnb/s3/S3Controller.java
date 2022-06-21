package louie.dong.airbnb.s3;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping
    public String form() {
        return "form";
    }

    @ResponseBody
    @PostMapping
    public void updateUserImage(@RequestParam("images") List<MultipartFile> multipartFiles)
        throws IOException {
        s3Service.uploadFiles(multipartFiles);
    }

    @ResponseBody
    @PostMapping("/v3")
    public String updateUserImage3(@RequestParam("images") MultipartFile multipartFile)
        throws IOException {
        return s3Service.uploadFileV2(multipartFile);
    }
}
