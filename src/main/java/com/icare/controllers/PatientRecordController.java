package com.icare.controllers;

import com.icare.db.entities.*;
import com.icare.db.repositories.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/patients")
public class PatientRecordController {
    @Autowired
    private PatientRecordRepository patientRecordRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private UserPasswordRepository userPasswordRepository;

    @Autowired
    private TreatmentRecordRepository treatmentRecordRepository;

    @Autowired
    private TreatmentRecordDrugRepository treatmentRecordDrugRepository;

    @Autowired
    private DrugsDictionaryRepository drugsDictionaryRepository;

    @Autowired
    private DocumentMetadataRepository documentMetadataRepository;

    String UPLOAD_PATH = "upload/";

    @GetMapping("")
    public String list(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterable<PatientRecord> patientRecords = null;

        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("worker"))) {
            UserPassword userPassword = userPasswordRepository.findByUserName(auth.getName());
            Worker worker = workerRepository.findById(userPassword.getUser().getId()).orElse(null);
            patientRecords = patientRecordRepository.findByWorker(worker);
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
            patientRecords = patientRecordRepository.findAll();
        }
        model.addAttribute("patientRecords", patientRecords);
        return "patient-record/list";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        PatientRecord patientRecord = new PatientRecord();
        model.addAttribute("patientRecord", patientRecord);
        return "patient-record/create";
    }

    @PostMapping("")
    public String create(PatientRecord patientRecord) {
        System.out.println(patientRecord);
        patientRecordRepository.save(patientRecord);
        return "redirect:/patients";
    }

    @PostMapping("/assign")
    public String assign(Long patientId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPassword userPassword = userPasswordRepository.findByUserName(auth.getName());
        Worker worker = workerRepository.findById(userPassword.getUser().getId()).orElse(null);
        PatientRecord patientRecord = patientRecordRepository.findById(patientId).
                orElse(null);
        patientRecord.setWorker(worker);
        patientRecordRepository.save(patientRecord);
        return "redirect:/icare-board";
    }

    @PostMapping("/unassign")
    public String unassign(Long patientId) {
        PatientRecord patientRecord = patientRecordRepository.findById(patientId).
                orElse(null);
        patientRecord.setWorker(null);
        patientRecordRepository.save(patientRecord);
        return "redirect:/patients";
    }

    @GetMapping("/details/{patientId}")
    public String details(Model model, @PathVariable Long patientId, HttpServletRequest request) {
        PatientRecord patientRecord = patientRecordRepository.findById(patientId).orElse(null);
        TreatmentRecord newTreatmentRecord = new TreatmentRecord();
        List<TreatmentRecordDrug> drugs = new ArrayList<>();
        drugs.add(new TreatmentRecordDrug());
        drugs.add(new TreatmentRecordDrug());
        newTreatmentRecord.setDrugs(drugs);
        newTreatmentRecord.setPatientRecord(patientRecord);

        Iterable<TreatmentRecord> treatmentRecords = treatmentRecordRepository.findByPatientRecord(patientRecord);

        HashMap<Long, String> files = new HashMap<>();
        String uploadFolder = request.getServletContext().getRealPath(UPLOAD_PATH);

        treatmentRecords.forEach(treatmentRecord -> {
            List<DocumentMetadata> documents = documentMetadataRepository.findByTreatmentRecord(treatmentRecord);
            documents.forEach(d -> {
                String path = d.getDocName();
                try {
//                    files.put(d.getDocId(), encodeFileToBase64(new File(path)));
                    files.put(d.getDocId(), path);
                } catch (Exception e) {

                }
            });
            treatmentRecord.setDocuments(documents);
        });
        System.out.println(files);

        model.addAttribute("treatmentRecord", newTreatmentRecord);
        model.addAttribute("drugsData", drugsDictionaryRepository.findAll());
        model.addAttribute("patientRecord", patientRecord);
        model.addAttribute("treatmentRecords", treatmentRecords);
        model.addAttribute("files", files);
        return "patient-record/details";
    }

    @GetMapping("/edit/{patientId}")
    public String editPage(Model model, @PathVariable Long patientId) {
        PatientRecord patientRecord = patientRecordRepository.findById(patientId).orElse(null);
        model.addAttribute("patientRecord", patientRecord);
        return "patient-record/edit";
    }

    @PostMapping("/{patientRecordId}/treatment-record")
    public String addTreatmentRecord(@PathVariable Long patientRecordId, TreatmentRecord treatmentRecord, @RequestParam("image") MultipartFile file, HttpServletRequest request) throws Exception {
        System.out.println(file);

        List<TreatmentRecordDrug> treatmentRecordDrugs = treatmentRecord.getDrugs();
        List<DrugsDictionary> allDrugs = drugsDictionaryRepository.findAll();
        HashMap<Long, DrugsDictionary> drugsMap = new HashMap<>();
        allDrugs.forEach(drug -> drugsMap.put(drug.getId(), drug));
        System.out.println(treatmentRecord.getDrugs());

        treatmentRecord.setDrugs(null);
        PatientRecord patientRecord = patientRecordRepository.findById(patientRecordId).orElse(null);
        treatmentRecord.setPatientRecord(patientRecord);

        System.out.println(treatmentRecordDrugs);
        // filter drugs without ids
        treatmentRecordDrugs = treatmentRecordDrugs.stream().filter(d -> d.getDrug() != null && d.getDrug().getId() != null).map(drug -> {
            Long drugId = drug.getDrug().getId();
            drug.setDrug(drugsMap.get(drugId));
            drug.setTreatmentRecord(treatmentRecord);
            return drug;
        }).collect(Collectors.toList());
        System.out.println(treatmentRecordDrugs);

        treatmentRecord.setDrugs(treatmentRecordDrugs);
        treatmentRecordRepository.save(treatmentRecord);
        treatmentRecordDrugRepository.saveAll(treatmentRecordDrugs);



        DocumentMetadata document = new DocumentMetadata();
        document.setTreatmentRecord(treatmentRecord);
        document.setDocName(file.getOriginalFilename());
        document.setDateOfCreation(new Date());
        documentMetadataRepository.save(document);

//        String filePath = "/static/upload/" + document.getJoinedName();
//        if (!new File(filePath).exists()) {
//            new File(filePath).mkdir();
//        }
//        File f = new File(filePath);
//        file.transferTo(f);

        FileController fileController = new FileController();
        document.setDocName(fileController.uploadImage(file, document.getJoinedName()));
        documentMetadataRepository.save(document);

        return "redirect:/patients/details/" + patientRecordId;
    }


    private static String encodeFileToBase64(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            System.out.println(Base64.getEncoder().encodeToString(fileContent));
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new IllegalStateException("could not read file " + file.toPath());
        } catch (Exception e) {
            throw new IllegalStateException("could not encode file " + file.toPath());
        }
    }


//    @GetMapping(
//            value = "/get-image",
//            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
//    )
//    public @ResponseBody byte[] getFile(HttpServletRequest request) throws IOException {
//        String uploadFolder = request.getServletContext().getRealPath("/uploads/");
//
//        InputStream in = getClass()
//                .getResourceAsStream("/com/baeldung/produceimage/data.txt");
//        return IOUtils.toByteArray(in);
//    }
}
