package cl.prestabanco.api.services;

import cl.prestabanco.api.models.JobsEntity;
import cl.prestabanco.api.models.UsersEntity;
import cl.prestabanco.api.repositories.JobsRepository;
import cl.prestabanco.api.utils.functions.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobsService {
    private final JobsRepository jobsRepository;
    private final UsersService usersService;

    @Autowired
    public JobsService(JobsRepository jobsRepository, UsersService usersService) {
        this.jobsRepository = jobsRepository;
        this.usersService = usersService;
    }

    public JobsEntity saveJob(String typeJob, String seniorityJob, Integer userJob) {
        // Search the user in database
        UsersEntity user = usersService.findUser(userJob);
        if (user == null) {
            return null;
        }

        // the job is saved in database
        JobsEntity job = new JobsEntity();
        job.setTypeJob(typeJob);
        job.setSeniorityJob(functions.transformStringtoDate(seniorityJob));
        job.setUserJob(user);
        return jobsRepository.save(job);
    }

    public JobsEntity findJob(Integer idJob) {
        return jobsRepository.findById(idJob).orElse(null);
    }

    public Boolean hasSeniority(Integer idUser) {
        JobsEntity job = jobsRepository.hasSeniority(idUser);
        return job != null;
    }
}
