package com.ex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ex.data.AdmissionsDTO;
import com.ex.entity.AdmissionsEntity;
import com.ex.repository.AdmissionsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class AdmissionsService {

    @Autowired
    private AdmissionsRepository admissionsRepository;

   
}
