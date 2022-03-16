package com.example.recoverbysporting.controller.sport;

import com.example.recoverbysporting.service.PrescribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prescribe")
public class PrescribeApi {
    @Autowired
    PrescribeService prescribeService;
    /**
     * 医生：
     *
     */
}
