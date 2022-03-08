package com.example.recoverbysporting.controller.patient;

import com.example.recoverbysporting.service.PatientService;
import com.example.recoverbysporting.utils.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient/manage")
public class PatientManageApi {
    @Autowired
    PatientService patientService;

    @RequestMapping("/allForManager")
    public Object findAllForManager(){
        return new ResultBody<>(true,200,patientService.findAll());
    }
}
