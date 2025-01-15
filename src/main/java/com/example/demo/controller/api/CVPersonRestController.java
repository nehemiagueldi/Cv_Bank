package com.example.demo.controller.api;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.Person;
import com.example.demo.model.Project;
import com.example.demo.model.dto.CVPersonDTO;
import com.example.demo.model.dto.CVPersonEditDTO;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.CVSkillRepository;
import com.example.demo.repository.CVToolRepository;
import com.example.demo.repository.EducationRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TrainingRepository;
import com.example.demo.repository.WorkExpRepository;
import com.example.demo.utils.CustomResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("api/cv-person")
public class CVPersonRestController {
    private CVPersonRepository cvPersonRepository;
    private PersonRepository personRepository;
    private ProjectRepository projectRepository;
    private EducationRepository educationRepository;
    private WorkExpRepository workExpRepository;
    private TrainingRepository trainingRepository;
    private CVToolRepository cvToolRepository;
    private CVSkillRepository cvSkillRepository;

    @Autowired
    public CVPersonRestController(CVPersonRepository cvPersonRepository, PersonRepository personRepository,
            ProjectRepository projectRepository, EducationRepository educationRepository,
            WorkExpRepository workExpRepository, TrainingRepository trainingRepository,
            CVToolRepository cvToolRepository, CVSkillRepository cvSkillRepository) {
        this.cvPersonRepository = cvPersonRepository;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
        this.educationRepository = educationRepository;
        this.workExpRepository = workExpRepository;
        this.trainingRepository = trainingRepository;
        this.cvToolRepository = cvToolRepository;
        this.cvSkillRepository = cvSkillRepository;
    }

    @GetMapping("percent")
    public ResponseEntity<Object> getData(@RequestParam("email") String email) {
        return CustomResponse.generate(HttpStatus.OK, "Data Found",
                cvPersonRepository.getPercentagerProgress(email));
    }

    @GetMapping
    public List<CVPersonDTO> get() {
        List<CVPerson> cvPersonList = cvPersonRepository.findAll();

        List<CVPersonDTO> cvPersonDTOList = new ArrayList<>();
        for (CVPerson cv : cvPersonList) {
            CVPerson cvPerson = cvPersonRepository.getCvByRandomString(cv.getRandomString());
            List<Project> projects = projectRepository.getByCVId(cvPerson.getId());
            int totalExperience = 0;
            List<YearMonth> countedMonths = new ArrayList<>();

            for (Project project : projects) {
                LocalDate startDate = project.getStart_date();
                LocalDate endDate = project.getEnd_date();

                int totalMonths = 0;
                LocalDate currentDate = startDate;

                while (!currentDate.isAfter(endDate)) {
                    YearMonth yearMonth = YearMonth.from(currentDate);

                    if (!countedMonths.contains(yearMonth)) {
                        totalMonths++;
                        countedMonths.add(yearMonth);
                    }

                    currentDate = currentDate.plusMonths(1);
                }

                totalExperience += totalMonths;
            }
            cvPersonDTOList.add(new CVPersonDTO(totalExperience,
                    Period.between(cvPerson.getPerson().getBirthdate(),
                            LocalDate.now()).getYears(),
                    cvPerson,
                    projects,
                    educationRepository.getByCVId(cvPerson.getId()),
                    workExpRepository.getByCVId(cvPerson.getId()),
                    trainingRepository.getByCVId(cvPerson.getId()),
                    cvToolRepository.getByCVId(cvPerson.getId()),
                    cvSkillRepository.getByCVId(cvPerson.getId())));
        }
        return cvPersonDTOList;

    }

    @GetMapping("{randomString}")
    public CVPersonDTO get(@PathVariable String randomString) {
        CVPerson cvPerson = cvPersonRepository.getCvByRandomString(randomString);
        return new CVPersonDTO(null, null, cvPerson,
                projectRepository.getByCVId(cvPerson.getId()),
                educationRepository.getByCVId(cvPerson.getId()),
                workExpRepository.getByCVId(cvPerson.getId()),
                trainingRepository.getByCVId(cvPerson.getId()),
                cvToolRepository.getByCVId(cvPerson.getId()),
                cvSkillRepository.getByCVId(cvPerson.getId()));
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestBody CVPerson cvPerson) {
        cvPerson.setPerson(personRepository.findById(cvPerson.getPerson().getId()).get());
        cvPersonRepository.save(cvPerson);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

    @PutMapping("edit/{randomString}")
    public ResponseEntity<Object> edit(@PathVariable String randomString, @RequestBody CVPersonEditDTO editDTO) {
        CVPerson cvPerson = cvPersonRepository.getCvByRandomString(randomString);
        cvPerson.setPosition(editDTO.getPosition());
        cvPerson.setSummary(editDTO.getSummary());
        cvPersonRepository.save(cvPerson);

        Person person = personRepository.findById(cvPerson.getId()).get();
        person.setName(editDTO.getName());
        personRepository.save(person);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }
}
