package tw.appworks.school.example.stylish.service.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;


public class S3Service {


    @Value("${s3.access.key}")
    private String s3AccessKey;

    @Value("${s3.secret.key}")
    private String s3SecretKey;

    private final String bucketName = "appwork23-stylish";

    public AmazonS3 createS3Client() {
        BasicAWSCredentials awsCredentials =
                new BasicAWSCredentials(s3AccessKey, s3SecretKey);
        AWSCredentials credentials = awsCredentials;
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();

        return s3Client;
    }

    public void getBucketsName(AmazonS3 s3Client) {
        List<Bucket> buckets = s3Client.listBuckets();
        System.out.println("Buckets:");
        //Dsiplay ALl the bucket name
        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName());
        }
    }

    public File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    public void uploadObject(AmazonS3 s3Client, String keyName, File file) {
        try {
//            String keyName = Paths.get(path).getFileName().toString();
//            s3Client.putObject(bucketName, keyName, new File(path));
            s3Client.putObject(new PutObjectRequest(bucketName, keyName, file));
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }
}
