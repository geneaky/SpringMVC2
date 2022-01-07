package hello.upload.file;

import hello.upload.domain.UploadFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStore {

  @Value("${file.dir}")
  private String fileDir;

  public String getFullPath(String filename) {
    return fileDir + filename;
  }

  public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
    List<UploadFile> storeFileResult = new ArrayList<>();
    for (MultipartFile multipartFile : multipartFiles) {
      if (!multipartFile.isEmpty()) {
        UploadFile uploadFile = storeFile(multipartFile);
        storeFileResult.add(uploadFile);
      }
    }
    return storeFileResult;
  }

  public UploadFile storeFile(MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      return null;
    }

    String originalFilename = file.getOriginalFilename();
    //image.png
    //서버에 저장하는 파일명
    String storeFileName = createStoreFileName(originalFilename);
    file.transferTo(new File(getFullPath(storeFileName)));
  return new UploadFile(originalFilename, storeFileName);
  }

  private String createStoreFileName(String originalFilename) {
    String uuid = UUID.randomUUID().toString();
    String ext = extractExt(originalFilename);
    String storeFileName = uuid + "." + ext;
    return storeFileName;
  }

  private String extractExt(String originalFilename) {
    int pos = originalFilename.lastIndexOf(".");
    String ext = originalFilename.substring(pos + 1);
    return ext;
  }

}
