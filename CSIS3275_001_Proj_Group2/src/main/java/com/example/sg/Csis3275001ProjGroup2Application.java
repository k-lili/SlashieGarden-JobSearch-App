package com.example.sg;

import java.time.LocalTime;


import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.sg.model.AppUser;
import com.example.sg.model.AppUserRepository;
import com.example.sg.model.Application;
import com.example.sg.model.ApplicationRepository;
import com.example.sg.model.Job;
import com.example.sg.model.JobRepository;
import com.example.sg.model.Status;
import com.example.sg.model.StatusRepository;
import com.example.sg.model.Type;
import com.example.sg.model.TypeRepository;
import com.example.sg.model.UserType;
import com.example.sg.model.UserTypeRepository;

@SpringBootApplication
public class Csis3275001ProjGroup2Application {

	public static void main(String[] args) {
		SpringApplication.run(Csis3275001ProjGroup2Application.class, args);
	}
	
	@Bean
	ApplicationRunner init(JobRepository jobRepo, StatusRepository statusRepo, AppUserRepository appUserRepo,
			ApplicationRepository applicationRepo, UserTypeRepository userTypeRepo, TypeRepository typeRepo) {
		return arg -> {
			
			statusRepo.save(Status.OPEN);
			statusRepo.save(Status.APPLIED);
			statusRepo.save(Status.ACCEPTED);
			statusRepo.save(Status.NOT_SELECTED);
			statusRepo.save(Status.HIRED);
			statusRepo.save(Status.COMPLETED);
			statusRepo.save(Status.CLOSED);
			statusRepo.save(Status.CLOSED_BY_ADMIN);
			
			typeRepo.save(Type.EMPLOYER);
			typeRepo.save(Type.APPLICANT);
			typeRepo.save(Type.ADMINISTRATOR);
			
			AppUser appUser1 = new AppUser("Herbert", "Lam", "male", "hlam@gmail.com", "(604)123-4567");
			AppUser appUser2 = new AppUser("Karli", "Li", "female", "kli@gmail.com", "(604)234-5678");
			AppUser appUser3 = new AppUser("Karl", "Chau", "male", "kchau@gmail.com", "(778)345-6789");
			AppUser appUser4 = new AppUser("Chi Ting", "Chui", "female", "ctchui@gmail.com", "(604)456-7890");
			
			appUserRepo.save(appUser1);
			appUserRepo.save(appUser2);
			appUserRepo.save(appUser3);
			appUserRepo.save(appUser4);
			
			UserType userType1 = new UserType(appUser1, Type.EMPLOYER);
			UserType userType2 = new UserType(appUser2, Type.EMPLOYER);
			UserType userType3 = new UserType(appUser2, Type.APPLICANT);
			UserType userType4 = new UserType(appUser3, Type.ADMINISTRATOR);
			UserType userType5 = new UserType(appUser3, Type.EMPLOYER);
			UserType userType6 = new UserType(appUser4, Type.APPLICANT);
			UserType userType7 = new UserType(appUser4, Type.ADMINISTRATOR);
			
			userTypeRepo.save(userType1);
			userTypeRepo.save(userType2);
			userTypeRepo.save(userType3);
			userTypeRepo.save(userType4);
			userTypeRepo.save(userType5);
			userTypeRepo.save(userType6);
			userTypeRepo.save(userType7);
			
			Job jobTest1 = new Job("House cleaner", "House cleaner job descriptions...", "04-01-2023", LocalTime.of(10, 30),
					LocalTime.of(12, 0), "123 Quebec St, Vancouver", 15.5, userType1);
			jobTest1.setStatus(Status.OPEN);
			Status.OPEN.addJob(jobTest1);
			Job jobTest2 = new Job("Gardener", "Gardener job descriptions...", "05-05-2023", LocalTime.of(14, 00),
					LocalTime.of(16, 30), "345 Victoria St, Burnaby", 20 ,userType5);
			jobTest2.setStatus(Status.OPEN);
			Status.OPEN.addJob(jobTest2);
			
			jobRepo.save(jobTest1);
			jobRepo.save(jobTest2);
			
			Application application1 = new Application(jobTest1, Status.APPLIED, userType3, "Mopping, Time Management");
			Application application2 = new Application(jobTest1, Status.APPLIED, userType6, "Irrigation, Trim Carpentry");
			
			applicationRepo.save(application1);
			applicationRepo.save(application2);
			System.out.println("Main Start Point is run Successfully!");
			
		};
	}
}
