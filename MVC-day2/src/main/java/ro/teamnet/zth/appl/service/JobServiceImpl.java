package ro.teamnet.zth.appl.service;

import ro.teamnet.zth.appl.dao.JobDao;
import ro.teamnet.zth.appl.domain.Job;

import java.util.List;

public class JobServiceImpl implements JobService {

    private final JobDao jobDao = new JobDao();

    @Override
    public List<Job> findAll() {
        return jobDao.getAllJobs();
    }

    @Override
    public Job findOne(String jobId) {
        return jobDao.getJobById(jobId);
    }

    @Override
    public Boolean delete(String jobId) {
        Job jobToDelete = jobDao.getJobById(jobId);
        if (jobToDelete == null) {
            return false;
        }
        jobDao.deleteJob(jobToDelete);
        return true;
    }

    @Override
    public Job save(Job job) {
        return jobDao.insertJob(job);
    }

    @Override
    public Job update(Job job) {
        return jobDao.updateJob(job);
    }
}
