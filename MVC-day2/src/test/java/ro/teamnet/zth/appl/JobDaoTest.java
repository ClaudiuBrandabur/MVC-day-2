package ro.teamnet.zth.appl;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ro.teamnet.zth.appl.dao.JobDao;
import ro.teamnet.zth.appl.domain.Job;

import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JobDaoTest {
    static Job job = new Job();
    JobDao jobDao = new JobDao();

    @Test
    public void aTestInsertJob() {
        job.setId("IT_DEP");
        job.setJobTitle("title");
        job.setMaxSalary(10);
        job.setMinSalary(1);
        job = jobDao.insertJob(job);

        assertEquals(jobDao.getJobById(job.getId()), job);

    }

    @Test
    public void bTestUpdateJob() {
        job.setMaxSalary(20);
        job = jobDao.updateJob(job);

        assertEquals(jobDao.getJobById(job.getId()), job);
    }

    @Test
    public void cTestDeleteJob() {
        jobDao.deleteJob(job);
        Job locById = jobDao.getJobById(job.getId());

        assertNull(locById);
    }

    @Test
    public void dGetAllJobs() {
        List<Job> oldLoc = jobDao.getAllJobs();
        //add new job
        job.setJobTitle("title");
        job.setMaxSalary(10);
        job.setMinSalary(1);
        jobDao.insertJob(job);
        List<Job> newLoc = jobDao.getAllJobs();

        assertEquals(oldLoc.size(), newLoc.size() - 1);
        jobDao.deleteJob(job);
    }
}
