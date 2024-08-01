package com.ex.controller;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.ex.data.DogsDTO;
import com.ex.service.DogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/dogs/*")
@RequiredArgsConstructor
public class DogsController {
	
	private final DogService dogService;
	
//	 (관리자) 강아지 전체 목록 출력
	@GetMapping("list")
	@PreAuthorize("isAuthenticated()")
	public String dogList(Model model) {
		model.addAttribute("dogsList", dogService.dogsAll());
		return "dogs/dogList";
	}
	
	@GetMapping("search")
	@PreAuthorize("isAuthenticated()")
	public String search(DogsDTO dogsDTO) {
		return "dogs/dogList";
	}
	
	@GetMapping("detail/{dog_id}")
	@PreAuthorize("isAuthenticated()")
	public String dogDetail(Model model, @PathVariable("dog_id") Integer id,
			DogsDTO dogsDTO, Principal principal) {
		dogsDTO = dogService.selectDog(id, principal.getName());
		model.addAttribute("dogdetail", dogsDTO);
		return "dogs/dogDetail";
	}
	
	@PostMapping("update")
	@PreAuthorize("isAuthenticated()")
	public String dogupdate(DogsDTO dogsDTO) {
		dogService.modifyDogs(dogsDTO);
		return String.format("redirect:/dogs/detail/%s", dogsDTO.getDog_id());
	}
	
	@GetMapping("addDogs")
	@PreAuthorize("isAuthenticated()")
	public String createDogs(DogsDTO dogsDTO) {
		return "dogs/myDogsInsert";
	}
	
	@PostMapping("addDogs")
	@PreAuthorize("isAuthenticated()")
	public String createDogs(@Valid DogsDTO dogsDTO, Principal principal) {
		dogService.addDogs(principal.getName(), dogsDTO);
		return "redirect:/members/mypage";
	}
	
	@GetMapping("myDogList")
	@PreAuthorize("isAuthenticated()")
	public String myDogList(DogsDTO dogsDTO, Principal principal, Model model) {
		List<DogsDTO> dogsList = dogService.myDogList(principal.getName());
		model.addAttribute("dogList", dogsList);
		return "dogs/myDogList";
	}
	
	@PostMapping("profile")
	@PreAuthorize("isAuthenticated()")
	public String myDogProfile(@RequestParam("profile") MultipartFile profile, 
			@RequestParam("dog_id") Integer id, Principal principal) {
		String sysname = dogService.profile(profile);
		DogsDTO dogsDTO = dogService.selectDog(id, principal.getName());
		if(sysname!=null) {
			if(dogsDTO.getDog_profile()==null) {
				dogService.createDogProfile(id, sysname);
			}else {
				dogService.deleteDogProfile(id);
				dogService.createDogProfile(id, sysname);
			}
		}
		return String.format("redirect:/dogs/detail/%s", id);
	}
	
	@GetMapping("display")
	public ResponseEntity<Resource> display(@RequestParam("filename") String filename) {
	   String path = "C:\\spring\\upload\\"; // 파일업로드된 경로
	   
	   // 파일명을 포함한 파일 경로로 FileSystemResource 객체를 생성 - 웹서버에 파일이 없기때문에 업로드된 파일을 읽어온다.
	   Resource resource = new FileSystemResource(path + filename);
	     
	   // 파일 있는지 확인
	   if(!resource.exists()) 
	      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND); // 파일이 없으면 404 not Found 전달
	     
	   // HTTP 응답 이미지 정보를 설정하기위한 객체
	   HttpHeaders header = new HttpHeaders(); 
	   Path filePath = null;
	   try{
	      // 파일의 MIME 타입을 결정하기위해 Path 클래스로 변환
	      filePath = Paths.get(path + filename);
	      // HTTP 응답이미지 타입으로 헤더에 추가 
	      header.add("Content-type", Files.probeContentType(filePath));
	   }catch(Exception e) {
	      e.printStackTrace();
	   }
	   // 파일 리소스와 200 OK 상태 코드를 포함한 ResponseEntity 객체를 반환 / 이래야 브라우저에서 다운로드및 이미지 출려이 가능하다.
	   return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}
}
