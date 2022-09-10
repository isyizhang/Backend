package furiends.backend.controller;


import furiends.backend.dto.PetRequest;
import furiends.backend.model.Pet;
import furiends.backend.service.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/pets" )
@Api(value = "宠物", tags = "宠物")
public class PetController {

    private static final Logger logger = LogManager.getLogger(PetController.class);

    @Autowired
    private PetService petService;

    // list all pets
    @GetMapping("")
    @ApiOperation(value="获取所有宠物列表")
    public ResponseEntity<List<Pet>> getAllPets() {
        try {
            return ResponseEntity.ok(petService.findAllPets());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // list all pet within the organization
    @GetMapping("/organization={organizationId}/pets")
    @ApiOperation(value="根据机构id获取机构所有宠物列表")
    public ResponseEntity<List<Pet>> getAllPetsWithinOrganization(@PathVariable("organizationId") String organizationId) {
        return ResponseEntity.ok(petService.findAllPetsWithinOrganization(organizationId));
    }

    // get pet by id
    @GetMapping("/{id}")
    @ApiOperation(value="根据宠物id获取宠物")
    public ResponseEntity<Pet> getPetById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(petService.findPetById(id).get());
        } catch (Exception e) {
            logger.error(e.toString());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // get pets by publish status (in edit or published), ordered by post's last update time
    @GetMapping("/published={isPublished}")
    @ApiOperation(value="根据发布状态获取所有宠物列表")
    public ResponseEntity<List<Pet>> getAllPetsByPublishStatus(@PathVariable("isPublished") boolean isPublished) {
        return ResponseEntity.ok(petService.findAllByPublishStatus(isPublished));
    }


    // (by organization) get pets by publish status (in edit or published), ordered by post's last update time
    @GetMapping("/organization={organizationId}/published={isPublished}")
    @ApiOperation(value="根据机构id和发布状态获取所有宠物列表")
    public ResponseEntity<List<Pet>> getAllPetsByPublishStatusOrg(@PathVariable("organizationId") String organizationId, @PathVariable("isPublished") boolean isPublished) {
        return ResponseEntity.ok(petService.findAllByPublishStatusOrg(organizationId, isPublished));
    }

    // get pets by adoption status, ordered by post's last update time
    @GetMapping("/adopted={isAdopted}")
    @ApiOperation(value="根据领养状态获取所有宠物列表")
    public ResponseEntity<List<Pet>> getAllPetsByAdoptionStatus(@PathVariable("isAdopted") boolean isAdopted) {
        return ResponseEntity.ok(petService.findAllByAdoptionStatus(isAdopted));
    }


    // (by organization) get pets by adoption status, ordered by post's last update time
    @GetMapping("/organization={organizationId}/adopted={isAdopted}")
    @ApiOperation(value="根据机构id和领养状态获取所有宠物列表")
    public ResponseEntity<List<Pet>> getAllPetsByAdoptionStatusOrg(@PathVariable ("organizationId") String organizationId, @PathVariable("isAdopted") boolean isAdopted) {
        return ResponseEntity.ok(petService.findAllByAdoptionStatusOrg(organizationId, isAdopted));
    }


    // create a pet
    @PostMapping("")
    @ApiOperation(value="创建宠物")
    public ResponseEntity createPet(@RequestBody PetRequest petRequest) {
        try {
            return ResponseEntity.ok(petService.createPet(petRequest));
        } catch (Exception e) {
            logger.error((e.toString()));
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // update a pet's info
    @PutMapping({"/{id}"})
    @ApiOperation(value="更新宠物信息")
    public ResponseEntity updatePet(@RequestBody PetRequest petRequest, @PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(petService.updatePet(petRequest, id));
        } catch (Exception e) {
            logger.error((e.toString()));
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // delete a pet
    @DeleteMapping ({"/{id}"})
    @ApiOperation(value="根据id删除宠物")
    public ResponseEntity deletePet(@PathVariable("id") String id) {
        try {
            petService.deletePet(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            logger.error((e.toString()));
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
