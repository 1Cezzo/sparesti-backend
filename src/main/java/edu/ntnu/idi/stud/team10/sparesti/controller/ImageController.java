package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import edu.ntnu.idi.stud.team10.sparesti.service.AmazonClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/** The ImageController class handles HTTP requests related to image storage. */
@RestController
@Tag(name = "Image", description = "Operations related to image storage")
@RequestMapping("/api/storage")
public class ImageController {
  private AmazonClient amazonClient;

  /**
   * Constructor for ImageController.
   *
   * @param amazonClient (AmazonClient) The Amazon client
   */
  @Autowired
  ImageController(AmazonClient amazonClient) {
    this.amazonClient = amazonClient;
  }

  /**
   * Uploads a file to the S3 bucket.
   *
   * @param file the file to upload
   * @return a ResponseEntity containing the URL of the uploaded file
   */
  @PostMapping("/uploadFile")
  @Operation(summary = "Upload a file")
  public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file) {
    String imageUrl = this.amazonClient.uploadFile(file);
    return ResponseEntity.ok(imageUrl);
  }

  /**
   * Deletes a file from the S3 bucket.
   *
   * @param payload the URL of the file to delete
   * @return a message indicating if the file was deleted successfully
   */
  @DeleteMapping("/deleteFile")
  @Operation(summary = "Delete a file")
  public ResponseEntity<Void> deleteFile(@RequestBody Map<String, String> payload) {
    this.amazonClient.deleteFileFromS3Bucket(payload.get("fileUrl"));
    return ResponseEntity.noContent().build();
  }
}
