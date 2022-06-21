package louie.dong.airbnb.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void uploadFiles(List<MultipartFile> multipartFiles)
        throws IOException {
        for (MultipartFile multipartFile : multipartFiles) {
            uploadFileV2(multipartFile);
        }
    }

    public String uploadFileV2(MultipartFile multipartFile) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        String key = "test/" + multipartFile.getOriginalFilename();


        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(
                new PutObjectRequest(bucket, key, inputStream, objectMetadata));
        }

        return amazonS3Client.getUrl(bucket, key).toString();
    }

    public String uploadFileV1(MultipartFile multipartFile) throws IOException {
//        MultipartFile 객체를 File 객체로 변경
        File uploadFile = new File(multipartFile.getOriginalFilename());

//        File 객체를 생성해야한다.
        if (uploadFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(uploadFile)) {
                fos.write(multipartFile.getBytes());
            }
        } else {
            throw new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다.");
        }

        String key = "test1/" + uploadFile.getName();
        amazonS3Client.putObject(new PutObjectRequest(bucket, key, uploadFile)
            .withCannedAcl(CannedAccessControlList.PublicRead));

//        위에서 생성한 File을 삭제한다.
        if (uploadFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }

//        bucket과 key를 통해 해당 객체의 URL을 조회할 수 있다.
        return amazonS3Client.getUrl(bucket, key).toString();
    }

}
