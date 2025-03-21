package com.example.demo.controller.api;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CVPerson;
import com.example.demo.model.CVSkill;
import com.example.demo.model.CVTool;
import com.example.demo.model.Education;
import com.example.demo.model.Person;
import com.example.demo.model.Project;
import com.example.demo.model.Skill;
import com.example.demo.model.Tool;
import com.example.demo.model.Training;
import com.example.demo.model.WorkExp;
import com.example.demo.model.dto.CVPersonAddDTO;
import com.example.demo.model.dto.CVPersonDTO;
import com.example.demo.model.dto.CVPersonEditDTO;
import com.example.demo.repository.CVPersonRepository;
import com.example.demo.repository.CVSkillRepository;
import com.example.demo.repository.CVToolRepository;
import com.example.demo.repository.EducationRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.SkillRepository;
import com.example.demo.repository.ToolRepository;
import com.example.demo.repository.TrainingRepository;
import com.example.demo.repository.WorkExpRepository;
import com.example.demo.utils.CustomResponse;

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
    private SkillRepository skillRepository;
    private ToolRepository toolRepository;

    @Autowired
    public CVPersonRestController(CVPersonRepository cvPersonRepository, PersonRepository personRepository,
            ProjectRepository projectRepository, EducationRepository educationRepository,
            WorkExpRepository workExpRepository, TrainingRepository trainingRepository,
            CVToolRepository cvToolRepository, CVSkillRepository cvSkillRepository, SkillRepository skillRepository,
            ToolRepository toolRepository) {
        this.cvPersonRepository = cvPersonRepository;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
        this.educationRepository = educationRepository;
        this.workExpRepository = workExpRepository;
        this.trainingRepository = trainingRepository;
        this.cvToolRepository = cvToolRepository;
        this.cvSkillRepository = cvSkillRepository;
        this.skillRepository = skillRepository;
        this.toolRepository = toolRepository;
    }

    @GetMapping("percent")
    public ResponseEntity<Object> getData(@RequestParam("email") String email) {
        return CustomResponse.generate(HttpStatus.OK, "Data Found",
                cvPersonRepository.getPercentagerProgress(email));
    }

    @GetMapping("{randomString}")
    public CVPersonDTO getByRandomString(@PathVariable String randomString) {
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
    public ResponseEntity<Object> post(@RequestBody CVPersonAddDTO cvPersonAddDTO) {
        Person person = new Person();
        person.setBirthdate(cvPersonAddDTO.getBirthdate());
        person.setEmail(cvPersonAddDTO.getEmail());
        person.setName(cvPersonAddDTO.getFirstName() + " " + cvPersonAddDTO.getLastName());
        person.setGender(cvPersonAddDTO.getGender());
        personRepository.save(person);

        CVPerson cvPerson = new CVPerson();
        cvPerson.setPosition("Developer");
        cvPerson.setPerson(person);
        cvPerson.setRandomString(cvPersonAddDTO.getRandomString());
        cvPersonRepository.save(cvPerson);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

    @Transactional
    @PutMapping("edit/{randomString}")
    public ResponseEntity<Object> edit(@PathVariable String randomString, @RequestBody CVPersonEditDTO editDTO) {
        CVPerson cvPerson = cvPersonRepository.getCvByRandomString(randomString);
        cvPerson.setPhoto_profile(editDTO.getPhotoProfile());
        cvPerson.setPosition(editDTO.getPosition());
        cvPerson.setSummary(editDTO.getSummary());
        cvPersonRepository.save(cvPerson);

        List<Integer> skillsToDelete = cvSkillRepository.getByCVId(cvPerson.getId())
                .stream()
                .map(CVSkill::getSkill)
                .map(Skill::getId)
                .filter(skillId -> !editDTO.getSkillsSelected().contains(skillId))
                .collect(Collectors.toList());

        for (Integer skillId : skillsToDelete) {
            cvSkillRepository.deleteByCvPersonIdAndSkillId(cvPerson.getId(), skillId);
        }

        List<Integer> currentSkillIds = cvSkillRepository.getByCVId(cvPerson.getId()).stream()
                .map(cvSkill -> cvSkill.getSkill().getId())
                .collect(Collectors.toList());
        List<Integer> skillsToAdd = editDTO.getSkillsSelected().stream()
                .filter(skillId -> !currentSkillIds.contains(skillId))
                .collect(Collectors.toList());
        for (Integer integer : skillsToAdd) {
            CVSkill cvSkill = new CVSkill();
            cvSkill.setCvPerson(cvPerson);
            cvSkill.setSkill(skillRepository.findById(integer).get());
            cvSkillRepository.save(cvSkill);
        }

        List<Integer> toolsToDelete = cvToolRepository.getByCVId(cvPerson.getId())
                .stream()
                .map(CVTool::getTool)
                .map(Tool::getId)
                .filter(toolId -> !editDTO.getToolsSelected().contains(toolId))
                .collect(Collectors.toList());

        for (Integer toolId : toolsToDelete) {
            cvToolRepository.deleteByCvPersonIdAndToolId(cvPerson.getId(), toolId);
        }

        List<Integer> currentToolIds = cvToolRepository.getByCVId(cvPerson.getId()).stream()
                .map(cvTool -> cvTool.getTool().getId())
                .collect(Collectors.toList());
        List<Integer> toolsToAdd = editDTO.getToolsSelected().stream()
                .filter(toolId -> !currentToolIds.contains(toolId))
                .collect(Collectors.toList());
        for (Integer integer : toolsToAdd) {
            CVTool cvTool = new CVTool();
            cvTool.setCvPerson(cvPerson);
            cvTool.setTool(toolRepository.findById(integer).get());
            cvToolRepository.save(cvTool);
        }

        List<WorkExp> currentWorkExps = workExpRepository.getByCVId(cvPerson.getId());
        List<Long> currentWorkExpIds = currentWorkExps.stream()
                .map(WorkExp::getId)
                .collect(Collectors.toList());
        List<Long> editWorkExpIds = editDTO.getWorkExp().stream()
                .map(WorkExp::getId)
                .collect(Collectors.toList());

        for (WorkExp workExp : currentWorkExps) {
            if (!editWorkExpIds.contains(workExp.getId())) {
                workExpRepository.deleteById(workExp.getId());
            }
        }

        for (WorkExp workExpEdit : editDTO.getWorkExp()) {
            if (currentWorkExpIds.contains(workExpEdit.getId())) {
                for (WorkExp workExp : currentWorkExps) {
                    if (workExp.getId().equals(workExpEdit.getId())) {
                        workExp.setName(workExpEdit.getName());
                        workExp.setDescription(workExpEdit.getDescription());
                        workExp.setCompany(workExpEdit.getCompany());
                        workExp.setStart_date(workExpEdit.getStart_date());
                        workExp.setEnd_date(workExpEdit.getEnd_date());
                        workExpRepository.save(workExp);
                        break;
                    }
                }
            } else {
                WorkExp newWorkExp = new WorkExp();
                newWorkExp.setName(workExpEdit.getName());
                newWorkExp.setDescription(workExpEdit.getDescription());
                newWorkExp.setCompany(workExpEdit.getCompany());
                newWorkExp.setStart_date(workExpEdit.getStart_date());
                newWorkExp.setEnd_date(workExpEdit.getEnd_date());
                newWorkExp.setCvPerson(cvPerson);
                workExpRepository.save(newWorkExp);
            }
        }

        List<Project> currentProjects = projectRepository.getByCVId(cvPerson.getId());
        List<Long> currentProjectIds = currentProjects.stream().map(Project::getId).collect(Collectors.toList());
        List<Long> editProjectIds = editDTO.getProjects().stream().map(Project::getId).collect(Collectors.toList());

        for (Project project : currentProjects) {
            if (!editProjectIds.contains(project.getId())) {
                projectRepository.deleteById(project.getId());
            }
        }

        for (Project projectEdit : editDTO.getProjects()) {
            if (currentProjectIds.contains(projectEdit.getId())) {
                for (Project project : currentProjects) {
                    if (project.getId() == projectEdit.getId()) {
                        project.setName(projectEdit.getName());
                        project.setDescription(projectEdit.getDescription());
                        project.setCompany(projectEdit.getCompany());
                        project.setStart_date(projectEdit.getStart_date());
                        project.setEnd_date(projectEdit.getEnd_date());
                        projectRepository.save(project);
                    }
                }
            } else {
                Project newProject = new Project();
                newProject.setName(projectEdit.getName());
                newProject.setDescription(projectEdit.getDescription());
                newProject.setCompany(projectEdit.getCompany());
                newProject.setStart_date(projectEdit.getStart_date());
                newProject.setEnd_date(projectEdit.getEnd_date());
                newProject.setCvPerson(cvPerson);
                projectRepository.save(newProject);

            }
        }

        List<Training> currentTrainings = trainingRepository.getByCVId(cvPerson.getId());
        List<Long> currentTrainingIds = currentTrainings.stream().map(Training::getId).collect(Collectors.toList());
        List<Long> editTrainingIds = editDTO.getTrainings().stream().map(Training::getId).collect(Collectors.toList());

        for (Training training : currentTrainings) {
            if (!editTrainingIds.contains(training.getId())) {
                trainingRepository.deleteById(training.getId());
            }
        }

        for (Training trainingEdit : editDTO.getTrainings()) {
            if (currentTrainingIds.contains(trainingEdit.getId())) {
                for (Training training : currentTrainings) {
                    if (training.getId() == trainingEdit.getId()) {
                        training.setName(trainingEdit.getName());
                        training.setDescription(trainingEdit.getDescription());
                        training.setCompany(trainingEdit.getCompany());
                        training.setStart_date(trainingEdit.getStart_date());
                        training.setEnd_date(trainingEdit.getEnd_date());
                        trainingRepository.save(training);
                    }
                }
            } else {
                Training newTraining = new Training();
                newTraining.setName(trainingEdit.getName());
                newTraining.setDescription(trainingEdit.getDescription());
                newTraining.setCompany(trainingEdit.getCompany());
                newTraining.setStart_date(trainingEdit.getStart_date());
                newTraining.setEnd_date(trainingEdit.getEnd_date());
                newTraining.setCvPerson(cvPerson);
                trainingRepository.save(newTraining);

            }
        }

        List<Education> currentEducations = educationRepository.getByCVId(cvPerson.getId());
        List<Long> currentEducationIds = currentEducations.stream().map(Education::getId).collect(Collectors.toList());
        List<Long> editEducationIds = editDTO.getEducations().stream().map(Education::getId)
                .collect(Collectors.toList());

        for (Education education : currentEducations) {
            if (!editEducationIds.contains(education.getId())) {
                educationRepository.deleteById(education.getId());
            }
        }
        for (Education educationEdit : editDTO.getEducations()) {
            if (currentEducationIds.contains(educationEdit.getId())) {
                for (Education education : currentEducations) {
                    if (education.getId() == educationEdit.getId()) {
                        education.setGpa(educationEdit.getGpa());
                        education.setStartDate(educationEdit.getStartDate());
                        education.setEndDate(educationEdit.getEndDate());
                        education.setDegree(educationEdit.getDegree());
                        education.setUniversity(educationEdit.getUniversity());
                        education.setMajor(educationEdit.getMajor());
                        educationRepository.save(education);
                    }
                }
            } else {
                Education newEducation = new Education();
                newEducation.setGpa(educationEdit.getGpa());
                newEducation.setStartDate(educationEdit.getStartDate());
                newEducation.setEndDate(educationEdit.getEndDate());
                newEducation.setDegree(educationEdit.getDegree());
                newEducation.setUniversity(educationEdit.getUniversity());
                newEducation.setMajor(educationEdit.getMajor());
                newEducation.setCvPerson(cvPerson);
                educationRepository.save(newEducation);

            }
        }

        Person person = personRepository.findById(cvPerson.getPerson().getId()).get();
        person.setName(editDTO.getName());
        person.setGender(editDTO.getGender());
        person.setBirthdate(editDTO.getBirthdate());
        personRepository.save(person);
        return CustomResponse.generate(HttpStatus.OK, "Data Saved");
    }

    @GetMapping
    @ResponseBody
    public Map<String, Object> getMapping(
            @RequestParam("draw") Integer draw,
            @RequestParam("start") Integer start,
            @RequestParam("length") Integer length,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "") String position,
            @RequestParam(required = false, defaultValue = "") String gender,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false, defaultValue = "") String skill,
            @RequestParam(required = false) Double gpa,
            @RequestParam(required = false, defaultValue = "") String major,
            @RequestParam(required = false, defaultValue = "") String university,
            @RequestParam(required = false, defaultValue = "") String company,
            @RequestParam(required = false, defaultValue = "") String jobDesc,
            @RequestParam(required = false) Integer experience) {

        if (start == null || start < 0)
            start = 0;
        if (length == null || length <= 0)
            length = 10;

        int ageStart = (age != null) ? age : 0;
        int ageEnd = (age != null && age <= 30) ? age == 20 ? age + 6 : age + 5 : 100;

        Double gpaStart = (gpa != null) ? gpa : null;
        Double gpaEnd = (gpa != null) ? (gpa == 3 ? gpa + 0.5 : gpa + 0.25) : null;

        List<String> skillList = (skill == null || skill.isBlank())
                ? Collections.emptyList()
                : Arrays.stream(skill.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());

        major = major.isBlank() ? null : major;
        university = university.isBlank() ? null : university;
        position = position.isBlank() ? null : position;

        company = company.isBlank() ? null : company;
        jobDesc = jobDesc.isBlank() ? null : jobDesc;

        long recordsTotal = cvPersonRepository.count();

        List<CVPerson> cvPersonList = cvPersonRepository.findCvPersonDTOs2(
                search, position, gender, ageStart, ageEnd, skillList, gpaStart, gpaEnd, major, university, company,
                jobDesc);

        List<Map<String, Object>> filteredCvPersons = cvPersonList.stream()
                .filter(cvPerson -> {
                    int totalExperienceMonths = calculateTotalExperience(cvPerson);
                    int totalExperienceYears = totalExperienceMonths / 12;
                    if (experience != null) {
                        if (experience == 4) {
                            return totalExperienceYears >= experience - 2 && totalExperienceYears <= experience;
                        } else if (experience == 9) {
                            return totalExperienceYears >= experience;
                        } else {
                            return totalExperienceYears >= experience - 1 && totalExperienceYears <= experience;
                        }
                    }
                    return true;
                })
                .map(cvPerson -> {
                    Map<String, Object> personData = new HashMap<>();
                    personData.put("cvPerson", cvPerson);
                    personData.put("totalExperience", calculateTotalExperience(cvPerson));
                    personData.put("age",
                            Period.between(cvPerson.getPerson().getBirthdate(), LocalDate.now()).getYears());
                    return personData;
                })
                .collect(Collectors.toList());

        int recordsFiltered = filteredCvPersons.size();

        int end = Math.min(start + length, recordsFiltered);

        List<Map<String, Object>> paginatedData = (start < recordsFiltered)
                ? filteredCvPersons.subList(start, end)
                : Collections.emptyList();

        Map<String, Object> response = new HashMap<>();
        response.put("draw", draw);
        response.put("recordsTotal", recordsTotal);
        response.put("recordsFiltered", recordsFiltered);
        response.put("data", paginatedData);

        return response;
    }

    private int calculateTotalExperience(CVPerson cvPerson) {
        List<WorkExp> workExps = workExpRepository.getByCVId(cvPerson.getId());
        int totalExperience = 0;
        List<YearMonth> countedMonths = new ArrayList<>();

        for (WorkExp workExp : workExps) {
            LocalDate startDate = workExp.getStart_date();
            LocalDate endDate = workExp.getEnd_date();

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

        return totalExperience;
    }

}