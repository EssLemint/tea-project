package com.lemint.tea.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUtil {

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  private final AmazonS3Client amazonS3Client;

  public String upload(MultipartFile multipartFile, String dirName) throws IOException {
    File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("파일 전환에 실패했습니다."));

    return upload(uploadFile, dirName);
  }

  private String upload(File file, String dirName) {
    String fileName = dirName + "/" + file.getName();
    String uploadFileUrl = putS3(file, fileName);

    deleteFile(file);
    // convert()함수로 인해서 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

    return uploadFileUrl;
    // 업로드된 파일의 S3 URL 주소 반환
  }

  private String putS3(File file, String fileName) {
    amazonS3Client.putObject(
        new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead)  //PublicRead
    );
    return amazonS3Client.getUrl(bucket, fileName).toString();
  }

  private void deleteFile(File targetFile) {
    if (targetFile.delete()) {
      log.info("삭제 성공");
    } else log.info("삭제 실패");
  }


  //MultipartFile to File
  private Optional<File> convert(MultipartFile file) throws IOException {
    File convertFile = new File(file.getOriginalFilename());  //upload파일 이름
    if (convertFile.createNewFile()) {
      try (FileOutputStream stream = new FileOutputStream(convertFile)) {
        stream.write(file.getBytes());
      }
      return Optional.of(convertFile);
    }
    return Optional.empty();
  }

}
