package com.icare.controllers;

import com.icare.db.entities.PatientRecord;
import com.icare.db.entities.TreatmentRecord;
import com.icare.db.repositories.PatientRecordRepository;
import com.icare.db.repositories.TreatmentRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "")
public class BoardController {
    @Autowired
    private PatientRecordRepository patientRecordRepository;

    @Autowired
    private TreatmentRecordRepository treatmentRecordRepository;

    @GetMapping("icare-board")
    public String icareBoard(Model model) {
        Iterable<PatientRecord> patientRecords = patientRecordRepository.findByNoWorker();
        model.addAttribute("patients", patientRecords);
        return "icareboard";
    }

    @GetMapping("palette")
    public String palette(Model model) {
        Iterable<TreatmentRecord> treatmentRecords = treatmentRecordRepository.findAll();
        model.addAttribute("treatmentRecords", treatmentRecords);
        return "palette";
    }
}

