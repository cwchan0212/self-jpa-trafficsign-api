package com.project.trafficsign.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.project.trafficsign.repository.CategoryRepository;
import com.project.trafficsign.repository.TrafficsignRepository;

import jakarta.annotation.PostConstruct;

@Configuration
public class InitialDataLoader {
    
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TrafficsignRepository trafficsignRepository;


    @PostConstruct
    public void loadInitialData() {

        // List<Category> categoryList = Arrays.asList(
        //     new Category("Signs giving orders", "48", new ArrayList<>()),
        //     new Category("Warning signs", "54", new ArrayList<>()),
        //     new Category("Direction signs", "30", new ArrayList<>()),
        //     new Category("Information signs", "24", new ArrayList<>()),
        //     new Category("Traffic work sign", "12", new ArrayList<>())
        // );

        // categoryRepository.saveAll(categoryList);

        // List<Trafficsign> trafficsignList = new ArrayList<>(Arrays.asList(
        //     new trafficsign("Entry to 20 mph zone", "sign-giving-order-entry-20-zone.jpg", "https://assets.digital.cabinet-office.gov.uk/media/55b9f29740f0b6151f000013/sign-giving-order-entry-20-zone.jpg", categoryList.get(0)),
        //     new trafficsign("End of 20 mph zone", "sign-giving-order-end-20-zonev2.jpg", "https://assets.publishing.service.gov.uk/media/57f76285e5274a0eb7000029/sign-giving-order-end-20-zonev2.jpg", categoryList.get(0)),
        //     new trafficsign("Maximum speed", "sign-giving-order-maximum-speed.jpg", "https://assets.digital.cabinet-office.gov.uk/media/55b9f2dd40f0b6151f000015/sign-giving-order-maximum-speed.jpg", categoryList.get(0)),
        //     new trafficsign("National speed limit applies", "sign-giving-order-national-speed-limit.jpg", "https://assets.digital.cabinet-office.gov.uk/media/55b9f2f740f0b6151c00001d/sign-giving-order-national-speed-limit.jpg", categoryList.get(0)),
        //     new trafficsign("School crossing patrol", "sign-giving-order-school-crossing-patrol.jpg", "https://assets.digital.cabinet-office.gov.uk/media/55b9f31040f0b6151f000017/sign-giving-order-school-crossing-patrol.jpg", categoryList.get(0))
        //  ));        

        // trafficsignList.forEach(trafficsign -> {
        //     Category category = trafficsign.getCategory();
        //     if (category != null) {
        //         category.getTrafficsignList().add(trafficsign);
        //     }
        // });
    
        // trafficsignRepository.saveAll(trafficsignList);

    }
    
}
