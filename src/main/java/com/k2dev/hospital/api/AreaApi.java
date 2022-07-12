package com.k2dev.hospital.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k2dev.hospital.service.AreaService;
import static com.k2dev.hospital.util.Constants.AREANAMES_NOT_FOUND_ERROR;
import static com.k2dev.hospital.util.Constants.STATES_NOT_FOUND_ERROR;
import static com.k2dev.hospital.util.Constants.DISTRICTS_NOT_FOUND_ERROR;
import static com.k2dev.hospital.util.Constants.PINCODES_NOT_FOUND_ERROR;

@RestController
@RequestMapping("/v1/area")
public class AreaApi {

	@Autowired
    private AreaService areaService;

    @GetMapping("/states")
    public ResponseEntity<?> findAllStates(){
        List<String> states= areaService.getStates();
        if(states==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(STATES_NOT_FOUND_ERROR);
        return ResponseEntity.ok().body(states);
    }

    @GetMapping("/states/{state}/districts")
    public ResponseEntity<?> findAllDistricts(@PathVariable("state") String state){
        List<String> districts= areaService.getDistricts(state);
        if(districts==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DISTRICTS_NOT_FOUND_ERROR);
        return ResponseEntity.ok().body(districts);
    }

    @GetMapping("/states/{state}/districts/{district}/pincodes")
    public ResponseEntity<?> findAllPincodes(@PathVariable("state") String state,@PathVariable("district") String district){
        List<String> pincodes= areaService.getPincodes(state,district);
        if(pincodes==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PINCODES_NOT_FOUND_ERROR);
        return ResponseEntity.ok().body(pincodes);
    }

    @GetMapping("/states/{state}/districts/{district}/pincodes/{pincode}/areaNames")
    public ResponseEntity<?> findAllAreaNames(@PathVariable("pincode") int pincode){
        List<String> areaNames= areaService.getAreaNames(pincode);
        if(areaNames==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(AREANAMES_NOT_FOUND_ERROR);
        return ResponseEntity.ok().body(areaNames);
    }
}
