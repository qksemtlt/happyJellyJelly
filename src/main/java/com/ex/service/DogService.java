package com.ex.service;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ex.data.DogsDTO;
import com.ex.entity.DogsEntity;
import com.ex.entity.MembersEntity;
import com.ex.repository.MembersRepository;
import com.ex.repository.DogsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DogService {

	private final DogsRepository dogRepository;
	private final MembersRepository membersRepository;	
	private final String UPLOAD_PATH="C:\\spring\\upload\\";
	
	public List<DogsEntity> dogsAll(){
		return dogRepository.findAll();
	}
	
	public DogsDTO selectDog(Integer id, String username){
		MembersEntity me = membersRepository.findByUsername(username).get();
		Optional<DogsEntity> dogEntityOptional = dogRepository.findById(id);
		DogsDTO dogsDTO = null;
		if(dogEntityOptional.isPresent()) {
			DogsEntity dogsEntity = dogEntityOptional.get();
			dogsDTO = DogsDTO.builder().dog_id(dogsEntity.getDogId()).dogname(dogsEntity.getDogname())
					.breed(dogsEntity.getBreed()).gender(dogsEntity.getGender()).birth_date(dogsEntity.getBirth_date())
					.dog_serialnum(dogsEntity.getDog_serialnum()).member(me)
					.neutering(dogsEntity.getNeutering()).dog_profile(dogsEntity.getDog_profile())
					.weight(dogsEntity.getWeight()).build();			
		}
		return dogsDTO;		
	}
	
	public void addDogs(String username, DogsDTO dogsDTO) {
		Optional<MembersEntity> op = membersRepository.findByUsername(username);
		if(op.isPresent()) {
			MembersEntity me = op.get();
			DogsEntity de = DogsEntity.builder().dogname(dogsDTO.getDogname()).breed(dogsDTO.getBreed())
					.weight(dogsDTO.getWeight()).birth_date(dogsDTO.getBirth_date())
					.gender(dogsDTO.getGender()).dog_serialnum(dogsDTO.getDog_serialnum())
					.neutering(dogsDTO.getNeutering()).member(me).build();
			dogRepository.save(de);
		}
	}
	
	public List<DogsDTO> myDogList(String username){
		Optional<MembersEntity> op = membersRepository.findByUsername(username);
		List<DogsDTO> list = null;
		DogsDTO dogsDTO = null;
		if(op.isPresent()) {
			MembersEntity me = op.get();
			List<DogsEntity> dogs = me.getDogs();
			list = new ArrayList<>(dogs.size());
			for(DogsEntity mydogs : dogs) {
				dogsDTO = new DogsDTO().builder().dog_id(mydogs.getDogId()).dogname(mydogs.getDogname()).breed(mydogs.getBreed())
						.birth_date(mydogs.getBirth_date()).gender(mydogs.getGender()).dog_serialnum(mydogs.getDog_serialnum())
						.dog_profile(mydogs.getDog_profile()).neutering(mydogs.getNeutering())
						.weight(mydogs.getWeight()).build();
				list.add(dogsDTO);
			}
		}
		return list;
	}
	
	public String profile(MultipartFile profile) {
		String orgname = profile.getOriginalFilename();
		String contentType = null;
		String sysname = null;
		String ext = null;
		File copy = null;
		if(profile!=null&&!orgname.equals("")) {
			contentType=profile.getContentType();
			if(contentType.split("/")[0].equals("image")) {
				sysname = UUID.randomUUID().toString().replace("-", "");
				ext = orgname.substring(orgname.lastIndexOf("."));
				sysname += ext;
				copy = new File(UPLOAD_PATH+sysname);
				try {
					profile.transferTo(copy);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return sysname;
	}
	
	public void createDogProfile(Integer id, String sysname) {
		Optional <DogsEntity> op = dogRepository.findById(id);
		if(op.isPresent()) {
			DogsEntity de = op.get();
			de.setDog_profile(sysname);
			dogRepository.save(de);
		}		
	}
	
	public boolean deleteDogProfile(Integer id) {
		DogsEntity de = dogRepository.findById(id).get();
		String sysname = de.getDog_profile();
		File file = new File(UPLOAD_PATH+sysname);
		return file.delete();
	}
	
	public void modifyDogs(DogsDTO dogsDTO) {
		Optional<DogsEntity> op = dogRepository.findById(dogsDTO.getDog_id());
		if(op.isPresent()) {
			DogsEntity de = op.get();
			de.setDogname(dogsDTO.getDogname());
			de.setBreed(dogsDTO.getBreed());
			de.setGender(dogsDTO.getGender());
			de.setBirth_date(dogsDTO.getBirth_date());
			dogRepository.save(de);
		}		
	}
	
	public List<DogsEntity> findDogsByMember(MembersEntity member) {
        return dogRepository.findByMember(member);
    }
}
