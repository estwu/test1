package com.mkyong.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mkyong.model.FileMetaData;
import com.mkyong.service.MetaDataService;

@Controller
public class UploadController {
	Logger logger = LoggerFactory.getLogger(UploadController.class);
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "c://tmp//";
    
    @Autowired
    MetaDataService dataService;

   
    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename()+".new");
            Files.write(path, bytes);
            saveMetaData(path.toFile());
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
   
	public void saveMetaData(File file) {

		try {
		  Path p = Paths.get(file.getAbsolutePath());
		  BasicFileAttributes view
		           = Files.getFileAttributeView(p, BasicFileAttributeView.class)
		                  .readAttributes();
		
		  long fileSize =view.size();
		  FileTime lastAccessTime =view.lastAccessTime();
		  String lastAccessTimeString = new SimpleDateFormat("dd/MM/yyyy").format((lastAccessTime.toMillis()));
		  FileMetaData md = new FileMetaData();
		  md.setFileSize(fileSize);
		  md.setLastAccessTime(lastAccessTimeString);
		  dataService.saveToDB(md);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@GetMapping("/metadata")
	public String getMetaData(RedirectAttributes redirectAttributes){
		
		List<FileMetaData> fileMetaDataList = dataService.getMetaData();
		logger.debug(fileMetaDataList.toString());
		String metaDataList =  fileMetaDataList.toString();
        redirectAttributes.addFlashAttribute("metaDataList",
                metaDataList);

        return "redirect:/metadataDisplay";
	}
    @GetMapping("/metadataDisplay")
    public String metadataDisplay() {
        return "metadataDisplay";
    }
 
//    @RequestMapping(value = "/download/{file_name:.+}", method = RequestMethod.GET)
//    public void downloadFile(      
//    	@PathVariable("file_name") String fileName, 
//        HttpServletResponse response) {
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(      
    	@RequestParam("fileName") String fileName, 
        HttpServletResponse response) {    	
        try {
          logger.debug("in downloadFile()");
          // get your file as InputStream
          Path path = Paths.get(UPLOADED_FOLDER + fileName);
         
          InputStream inputStream = new FileInputStream(path.toFile());
          
          response.setContentType("application/force-download");
          response.setHeader("Content-Disposition", "attachment; filename="+"\""+fileName+"\""); 
          // copy it to response's OutputStream
          org.apache.commons.io.IOUtils.copy(inputStream, response.getOutputStream());
          response.flushBuffer();
          inputStream.close();


        } catch (IOException ex) {
        	 logger.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
          throw new RuntimeException("IOError writing file to output stream");
        }

    }

}