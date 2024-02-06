package com.hexaware.view.main;



import java.util.List;
import java.util.Scanner;

import com.hexaware.controller.dao.ApplicantDAO;
import com.hexaware.controller.dao.CompanyDAO;
import com.hexaware.controller.dao.JobApplicationDAO;
import com.hexaware.controller.dao.JobListingDAO;
import com.hexaware.controller.dao.Impl.ApplicantDAOImpl;
import com.hexaware.controller.dao.Impl.CompanyDAOImpl;
import com.hexaware.controller.dao.Impl.JobApplicationDAOImpl;
import com.hexaware.controller.dao.Impl.JobListingDAOImpl;
import com.hexaware.exception.InvalidEmailException;
import com.hexaware.model.entity.Applicant;
import com.hexaware.model.entity.Company;
import com.hexaware.model.entity.JobApplication;
import com.hexaware.model.entity.JobListing;

public class MainModule {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CompanyDAO companyDAO = new CompanyDAOImpl();
    private static final JobListingDAO jobListingDAO = new JobListingDAOImpl();
    private static final JobApplicationDAO jobApplicationDAO = new JobApplicationDAOImpl();
    private static final ApplicantDAO applicantDAO = new ApplicantDAOImpl();

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("1. Post a Job");
            System.out.println("2. View Jobs");
            System.out.println("3. Apply for a Job");
            System.out.println("4. View Job Applications");
            System.out.println("5. Applicant Signup");
            System.out.println("6. View Jobs in a specific range");
            System.out.println("7. Exit");
            

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    postJob();
                    break;
                case 2:
                    viewJobs();
                    break;
                case 3:
                    applyForJob();
                    break;
                case 4:
                    viewJobApplications();
                    break;
                case 5:
                	try {
                		applicantDetails();
					} catch (InvalidEmailException e) {
					 System.out.println(e);
					}
                	
                	break;
                case 6:
                	getJobsInRange();
                	break;
                case 7:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 5);

        
        scanner.close();
    }

    private static void getJobsInRange() {
    	System.out.print("Range from : ");
    	double fromSal = scanner.nextDouble();
    	
    	System.out.print("To : ");
    	double toSal = scanner.nextDouble();
    	
    	List<JobListing>jobsInrange=jobListingDAO.getJobListingsInRange(fromSal, toSal);
    	if (jobsInrange.isEmpty()) {
            System.out.println("No jobs available at the moment.");
        } else {
            System.out.println("List of available jobs:");
            for (JobListing jobListing : jobsInrange) {
                System.out.println("Job ID: " + jobListing.getJobID());
                System.out.println("Title: " + jobListing.getJobTitle());
                System.out.println("Company: " + getCompanyName(jobListing.getCompanyID()));
                System.out.println("Location: " + jobListing.getJobLocation());
                System.out.println("Salary: " + jobListing.getSalary());
                System.out.println("Type: " + jobListing.getJobType());
                System.out.println("Posted Date: " + jobListing.getPostedDate());
                System.out.println("----------------------");
            }
        }
    	
		
	}

	private static void applicantDetails() throws InvalidEmailException {
    	System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter your mobile no. : ");
        String mobile = scanner.nextLine();
        
        System.out.print("Enter your resume link: ");
        String resume = scanner.nextLine();
        
        Applicant applicant = new Applicant();
        if(email.contains("@")) {
        	applicant.setEmail(email);
        }else throw new InvalidEmailException("Invalid Email");
        
        applicant.setFirstName(firstName);
        applicant.setLastName(lastName);
        
        applicant.setPhone(mobile);
        applicant.setResume(resume);
        
        applicantDAO.insertApplicant(applicant);
		
	}

	private static void postJob() {
        System.out.print("Enter your company name: ");
        String companyName = scanner.nextLine();

        System.out.print("Enter the job title: ");
        String jobTitle = scanner.nextLine();

        System.out.print("Enter the job description: ");
        String jobDescription = scanner.nextLine();

        System.out.print("Enter the job location: ");
        String jobLocation = scanner.nextLine();

        System.out.print("Enter the salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); 

        System.out.print("Enter the job type: ");
        String jobType = scanner.nextLine();
        
        System.out.println("Enter Comany ID");
        int companyID = scanner.nextInt();
        

        Company company = new Company();
        company.setCompanyID(companyID);
        company.setCompanyName(companyName);
        company.setLocation(jobLocation);

        companyDAO.insertCompany(company);

        JobListing jobListing = new JobListing();
        jobListing.setCompanyID(companyID);
        jobListing.setJobTitle(jobTitle);
        jobListing.setJobDescription(jobDescription);
        jobListing.setJobLocation(jobLocation);
        jobListing.setSalary(salary);
        jobListing.setJobType(jobType);

        jobListingDAO.insertJobListing(jobListing);

        System.out.println("Job posted successfully!");
    }

    private static void viewJobs() {
        List<JobListing> jobListings = jobListingDAO.getJobListings();

        if (jobListings.isEmpty()) {
            System.out.println("No jobs available at the moment.");
        } else {
            System.out.println("List of available jobs:");
            for (JobListing jobListing : jobListings) {
                System.out.println("Job ID: " + jobListing.getJobID());
                System.out.println("Title: " + jobListing.getJobTitle());
                System.out.println("Company: " + getCompanyName(jobListing.getCompanyID()));
                System.out.println("Location: " + jobListing.getJobLocation());
                System.out.println("Salary: " + jobListing.getSalary());
                System.out.println("Type: " + jobListing.getJobType());
                System.out.println("Posted Date: " + jobListing.getPostedDate());
                System.out.println("----------------------");
            }
        }
    }

    private static void applyForJob() {
        System.out.print("Enter the Job ID you want to apply for: ");
        int jobID = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter your Applicant ID: ");
        int applicantID = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter your cover letter: ");
        String coverLetter = scanner.nextLine();

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobID(jobID);
        jobApplication.setApplicantID(applicantID);
        jobApplication.setCoverLetter(coverLetter);

        jobApplicationDAO.insertJobApplication(jobApplication);

        System.out.println("Application submitted successfully!");
    }

    private static void viewJobApplications() {
        System.out.print("Enter the Job ID to view applications: ");
        int jobID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        List<JobApplication> jobApplications = jobApplicationDAO.getApplicationsForJob(jobID);

        if (jobApplications.isEmpty()) {
            System.out.println("No applications for this job.");
        } else {
            System.out.println("List of job applications for Job ID " + jobID + ":");
            for (JobApplication jobApplication : jobApplications) {
                System.out.println("Application ID: " + jobApplication.getApplicationID());
                System.out.println("Applicant ID: " + jobApplication.getApplicantID());
                System.out.println("Application Date: " + jobApplication.getApplicationDate());
                System.out.println("Cover Letter: " + jobApplication.getCoverLetter());
                System.out.println("----------------------");
            }
        }
    }

    private static String getCompanyName(int companyID) {
        Company company = companyDAO.getCompanyById(companyID);
        return (company != null) ? company.getCompanyName() : "Unknown";
    }
}

