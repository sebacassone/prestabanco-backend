package cl.prestabanco.api.services;

import cl.prestabanco.api.models.JobsEntity;
import cl.prestabanco.api.models.UsersEntity;
import cl.prestabanco.api.repositories.JobsRepository;
import cl.prestabanco.api.utils.functions.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class JobsService {
    @Autowired
    private JobsRepository jobsRepository;
    @Autowired
    private UsersService usersService;

    public JobsEntity saveJob(String typeJob, String seniorityJob, Object userJob) {
        // Search the user in database
        UsersEntity user = usersService.findUser(functions.transformLong(userJob));

        // the job is saved in database
        JobsEntity job = new JobsEntity();
        job.setTypeJob(typeJob);
        job.setSeniorityJob(functions.transformStringtoDate(seniorityJob));
        job.setUserJob(user);
        return jobsRepository.save(job);
    }

    public JobsEntity findJob(Long idJob) {
        return jobsRepository.findById(idJob).orElse(null);
    }
}
