package com.study.event.api.event.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class AwsS3Service {

    //s3 버킷 제어하는 객체
    private S3Client s3;

    //인증정보
    @Value("${aws.credentials.accessKey}")
    private String accessKey;
    @Value("${aws.credentials.secretKey}")
    private String secretKey;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.bucketName}")
    private String bucketName;

    //aws s3에 접근하여 인증
    @PostConstruct // 본 서비스가 생성될 때 단 1번 실행
    private void initAmazonS3() {
        //액세스키와 비밀키로 사용자 인증
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        this.s3 = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    /**
     * 버킷에 파일을 업로드하고 업로드한 버킷의 URL을 리턴
     * @param uploadFile - 파일의 바이너리
     * @param fileName - 저장할 파일명
     * @return - 저장된 URL
     */
    public String uploadToS3Bucket(byte[] uploadFile, String fileName) {
        // 현재 날짜를 기반으로 폴더 생성
        // 2024-07-22   =>   2024/07/22
        String datePath = LocalDate.now().toString().replace("-", "/");


        String fullPath = datePath + "/"+fileName;

        // upload
        PutObjectAclRequest request = PutObjectAclRequest.builder()
                .bucket(bucketName)
                .key(fullPath)
                .build();

        s3.putObject(request, RequestBody.fromBytes(uploadFile));


        //업로드된 경로 url 을 반환
        return s3.utilities()
                .getUrl(b -> b.bucket(bucketName).key(fullPath))
                .toString()
                ;
    }


}
