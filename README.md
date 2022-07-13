# online-hospital-services
Provides online hospital related services to its end users e.g. searching doctors, hospitals, blood, injection. Request for home tests. Scheduling appointments

## Azure Screenshots
![](Screenshots/subscription.PNG)

### Resource Group
![](Screenshots/resource_group.PNG)

### MySQL database
![](Screenshots/database_overview.PNG)

### App Service(Compute)
![](Screenshots/app_service_overview.PNG)
![](Screenshots/app_service_config.PNG)

### Website Home Page
![](Screenshots/home_page.PNG)

### Backend APIs
- Hospitals: (https://online-hospital-services.azurewebsites.net/v1/public/hospitals?filters=none)
- Doctors: (https://online-hospital-services.azurewebsites.net/v1/public/doctors?filters=none)
- Labs: (https://online-hospital-services.azurewebsites.net/v1/public/labs?filters=none)
- Products: (https://online-hospital-services.azurewebsites.net/v1/public/products?filters=none)

## Admin Dashboard
![](Screenshots/admin_dashboard.PNG)

## Hospital-Admin Dashboard
![](Screenshots/ha_home.PNG)

## Patient Dashboard
![](Screenshots/patient_home.PNG)

## Username & Passwords
- Admin
  - username: admin@hs.com
  - password: 12345@Aa
- Hospital-Admin
  - username: ha111@gmail.com
  - password: 12345@Aa
- Patient
  - username: p123@gmail.com
  - password: 12345@Aa
  ## Developed Using
  - Angular
  - SpringBoot
  - MySql 

  ## Angular Frontend Repository
  - [Online-Hospital-Services-Frontend](https://github.com/kamalkaryat/online-hospital-services-frontend)
  
  ## Deployment
  - SpringBoot backend application on Azure as App Service

  - Angular frontend application on Github Pages
  - [Website](https://kamalkaryat.github.io/)
  
  
  ## Security
  - Spring Security
  - JWT

  ## Type of Roles:
  1. User/Patient
  2. Hospital-Admin
  3. Doctor
  4. Admin
  
  ## Patient[or User] 
  - Register with this platform
  - Search for hospitals, doctors, labs, products(such as blood, tests, injections, etc)
  - Book, cancel & reschedule appointments in any hospital which are registered  with this platform.
  - Book products, so that later they can purchase them from the corresponding hospitals
  - Request for a home tests if possible.
  - Manage his/her profile
  
  ## Hospital-Admin
  - Add and remove doctors
  - Add & remove appointments from their hospital
  - Can link a product with this platform for online services
  - Remove linked products from this platform
  - Can Update products stock of their hospital
  - Accept or reject users test requests
  - Upload patient's[user's] test report. This feature will be available in future updates.
 
  ## Admin
  - Manage Hospitals
  - Manage Labs
  - Manage Doctors
  - Manage Patients[or Users]
  - Manage Hospital-Admins
  - Manage Products
  
  ## Registration Process
  - Patients/Users can simply use signup form for their registration
  - Admin will register Hospital-Admins  
  - Hospital-Admin will register Doctors
