package com.springboot.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.springboot.api.dao.repository.AdminUserRepository;
import com.springboot.api.dao.repository.CustomerApiKeysRepository;
import com.springboot.api.dao.repository.CustomerApiLimitsRepository;
import com.springboot.api.dao.repository.CustomerProductRepository;
import com.springboot.api.dao.repository.CustomerUserRepository;
import com.springboot.api.dao.repository.ProductRepository;
import com.springboot.api.dao.repository.ProductTracksRepository;
import com.springboot.api.dao.repository.TracksRepository;
import com.springboot.api.domain.AdminAction;
import com.springboot.api.domain.AdminUser;
import com.springboot.api.domain.ApiRequest;
import com.springboot.api.domain.CustomerApiKey;
import com.springboot.api.domain.CustomerApiLimit;
import com.springboot.api.domain.CustomerProduct;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.domain.Product;
import com.springboot.api.domain.ProductTrack;
import com.springboot.api.domain.Track;
import com.springboot.api.endpoint.to.CustomerProductTo;
import com.springboot.api.enums.UserRoleCodeEnum;
import com.springboot.api.to.CustomerApiKeyTo;
import com.springboot.api.to.CustomerApiLimitTo;
import com.springboot.api.to.CustomerUserTo;
import com.springboot.api.to.PrincipalUser;
import com.springboot.api.to.ProductTrackTo;
import com.springboot.api.to.ReportLogCriteria;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.to.TrackTo;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class AbstractTest {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerProductRepository customerProductRepository;
	@Autowired
	private ProductTracksRepository productTracksRepository;
	
	@Autowired
	private TracksRepository tracksRepository;
	
	@Autowired
	private CustomerUserRepository customerRepository;
	
	@Autowired
	private ProductTracksRepository ProductTrackRepository;
	
	@Autowired
	private AdminUserRepository adminUserRepository;
	
	@Autowired
	private CustomerApiKeysRepository customerApiKeyRepository;
	
	@Autowired
	private CustomerApiLimitsRepository customerApiLimitsRepository;
	
	private Track track;
	private Product product;
	private CustomerUser customer;
	private ProductTrack productTrack;
	private CustomerProduct customerProduct;
	private AdminUser admin;
	private CustomerApiKey customerApiKey;
	
	private CustomerApiLimit customerApiLimit;
	
	public CustomerApiKey insertTestCustomerApiKey(boolean isActive) {
		customerApiKey = new CustomerApiKey();
		customerApiKey.setCustomerId(customer.getId());
		customerApiKey.setApiKey("Junit Test Api Key");
		customerApiKey.setIsActive(isActive);
		customerApiKey.setTimeStamp(new Date());
		if(!isActive) {
			customerApiKey.setDisabledAt(new Date());
		}
		//Inserted
		customerApiKey = customerApiKeyRepository.save(customerApiKey);
		return customerApiKey;
	}
	
	public Product insertTestProduct() {
		product = new Product();
		product.setLabel("product1");
		product.setType("type" + new Date().getTime());
		product.setLevel("level" + new Date().getTime());
		product.setCacheTime(20);
		productRepository.save(product);
		return product;
	}
	
	public CustomerUser insertTestCustomerUser() {
		customer = new CustomerUser();
		customer.setIsActive(true);
		customer.setLoginId("junit" + new Date().getTime());
		customer.setEmail("test@gmail.com");
		customer.setBaseAccessLimit(2000L);
		customer.setCompanyName("Menlo");
		String becryptedPwd = Util.getBCrptPassword("MenloTechnologies@123");
		customer.setPassword(becryptedPwd.getBytes());
		customerRepository.save(customer);
		return customer;
	}
	
	public CustomerApiLimit insertTestCustomerApiLimits() {
		customerApiLimit = new CustomerApiLimit();
		customerApiLimit.setCurrentAccessCount(2000L);
		customerApiLimit.setCurrentAccessLimit(3000L);
		customerApiLimit.setCustomerId(customer.getId());
		customerApiLimit.setStartDate(Util.getFirstDateOfCurrentMonth());
		customerApiLimit.setEndDate(Util.getLastDateOfCurrentMonth());
		customerApiLimitsRepository.save(customerApiLimit);
		return customerApiLimit;
	}
	
	public AdminUser insertTestAdmin() {
		admin = new AdminUser();
		admin.setLoginId("junit" + new Date().getTime());
		String pwd = Util.getBCrptPassword("MenloTechnologies@123");
		admin.setPassword(pwd.getBytes());
		admin.setIsActive(true);
		admin.setAccessLevel(5);
		admin.setName("Junit");
		//Inserted
		adminUserRepository.save(admin);
		return admin;
	}
	
	public ProductTrack insertTestProductTrack() {
		List<Track> tracks = tracksRepository.findAll();
		if (tracks != null && !tracks.isEmpty()) {
			track = tracks.get(0);
		}
		productTrack = new ProductTrack();
		productTrack.setCustomerProductId(customerProduct.getId());
		productTrack.setTrackId(track.getId());
		ProductTrackRepository.save(productTrack);
		return productTrack;
	}
	
	public CustomerProduct insertTestCustomerProduct() {
		customerProduct = new CustomerProduct();
		customerProduct.setAllTracks(true);
		customerProduct.setCustomerId(customer.getId());
		customerProduct.setDaysBack(3);
		customerProduct.setIsActive(true);
		customerProduct.setProductId(product.getId());
		customerProductRepository.save(customerProduct);
		return customerProduct;
	}
	
	/**
	 * Mock Authentication
	 * @return
	 */
	public Authentication getMockAuthentication() {
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(UserRoleCodeEnum.LEVEL_1.getValue()));
		
		UserDetails userDetails = new PrincipalUser("Anil",
				"$2a$10$QxLd7vQnoUVYdWnJosb7WOliaM.mLVMQ9/pjj8epVr/8OGaQoOQaG", authorities, 1L);
		
		if(userDetails!=null && !userDetails.getAuthorities().isEmpty()){
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return authentication;
		}
		return null;
	}
	
	
	public CustomerUser buildMockCustomer(){
		CustomerUser customer = new CustomerUser();
		customer.setId(1L);
		customer.setIsActive(true);
		customer.setLoginId("junit" + new Date().getTime());
		customer.setEmail("test@gmail.com");
		customer.setBaseAccessLimit(2000L);
		customer.setCompanyName("Menlo");
		String becryptedPwd = Util.getBCrptPassword("MenloTechnologies@123");
		customer.setPassword(becryptedPwd.getBytes());
		customer.setPasswordStr("MenloTechnologies@123");
		return customer;
	}
	
	public CustomerUserTo buildMockCustomerUserTo(boolean allTracks){
		CustomerUserTo customerUserTo = new CustomerUserTo();
		customerUserTo.setBaseAccessLimit(5000L);
		customerUserTo.setCompanyName("Menlo");
		customerUserTo.setEmail("info@gmail.com");
		customerUserTo.setId(1L);
		customerUserTo.setLoginId("junit123");
		customerUserTo.setPassword(Util.getBCrptPassword("MenloTechnologies@123").getBytes());
		
		List<CustomerProductTo> customerProducts = new ArrayList<CustomerProductTo>();
		customerProducts.add(buildMockCustomerProductTo(allTracks));
	    
	    List<CustomerApiKeyTo> customerApiKeys = new ArrayList<CustomerApiKeyTo>();
	    customerApiKeys.add(buildMockCustomerApiKeyTo());
	    
	    CustomerApiLimitTo customerApiLimit = buildMockCustomerApiLimitTo();
		
	    customerUserTo.setCustomerProducts(customerProducts);
		customerUserTo.setCustomerApiKeys(customerApiKeys);
		customerUserTo.setCustomerApiLimit(customerApiLimit);
		
		
		return customerUserTo;
		
	}
	
	public CustomerApiKey buildMockCustomerApiKey(){
		CustomerApiKey customerApiKey = new CustomerApiKey();
		customerApiKey.setId(1L);
		customerApiKey.setCustomerId(1L);
		customerApiKey.setApiKey("Junit Test Api Key");
		customerApiKey.setIsActive(true);
		customerApiKey.setTimeStamp(new Date());
		return customerApiKey;
	}
	
	public CustomerApiKeyTo buildMockCustomerApiKeyTo(){
		CustomerApiKeyTo customerApiKeyTo = new CustomerApiKeyTo();
		customerApiKeyTo.setId(1L);
		customerApiKeyTo.setCustomerId(1L);
		customerApiKeyTo.setApiKey("Junit Test Api Key");
		customerApiKeyTo.setIsActive(true);
		customerApiKeyTo.setTimeStamp(new Date());
		return customerApiKeyTo;
	}
	
	public CustomerProduct buildMockCustomerProduct(boolean allTracks){
		CustomerProduct customerProduct = new CustomerProduct();
		customerProduct.setId(1L);
		customerProduct.setAllTracks(allTracks);
		customerProduct.setCustomerId(1L);
		customerProduct.setDaysBack(3);
		customerProduct.setIsActive(true);
		customerProduct.setProductId(1L);
		return customerProduct;
	}
	
	public CustomerProductTo buildMockCustomerProductTo(boolean allTracks){
		CustomerProductTo customerProductTo = new CustomerProductTo();
		customerProductTo.setId(1L);
		customerProductTo.setCustomerId(1L);
		customerProductTo.setDaysBack(3);
		customerProductTo.setIsActive(true);
		customerProductTo.setProductId(1L);
		
		
		customerProductTo.setAllTracks(allTracks);
		if(!allTracks) {
			List<ProductTrackTo> productTracks = new ArrayList<ProductTrackTo>();
			ProductTrackTo productTrackTo = buildMockProductTrackTo();
			productTracks.add(productTrackTo);
			customerProductTo.setProductTracks(productTracks);
			
		}
		
		
		return customerProductTo;
	}
	
	public Product buildMockProduct(){
		Product product = new Product();
		product.setId(1L);
		product.setLabel("product1");
		product.setType("type" + new Date().getTime());
		product.setLevel("level" + new Date().getTime());
		product.setCacheTime(20);
		return product;
	}
	
	public ProductTrack buildMockProductTrack(){
		ProductTrack productTrack = new ProductTrack();
		productTrack.setId(1L);
		productTrack.setCustomerProductId(1L);
		productTrack.setTrackId("XXX");
		return productTrack;
	}
	
	public ProductTrackTo buildMockProductTrackTo(){
		ProductTrackTo productTrackTo = new ProductTrackTo();
		productTrackTo.setId(1L);
		productTrackTo.setCustomerProductId(1L);
		productTrackTo.setTrackId("XXX");
		productTrackTo.setTrack(builMockTrackTo());
		return productTrackTo;
	}
	
	public Track builMockTrack(){
		Track track = new Track();
		track.setId("XXX");
		track.setCountry("India");
		track.setName("test");
		track.setType("type");
		return track;
	}
	
	public TrackTo builMockTrackTo(){
		TrackTo track = new TrackTo();
		track.setId("XXX");
		track.setCountry("India");
		track.setName("test");
		track.setType("type");
		return track;
	}
	

	public CustomerApiLimit buildMockCustomerApiLimit() {
		CustomerApiLimit customerApiLimit = new CustomerApiLimit();
		customerApiLimit.setId(1L);
		customerApiLimit.setCurrentAccessCount(2000L);
		customerApiLimit.setCurrentAccessLimit(3000L);
		customerApiLimit.setCustomerId(1L);
		customerApiLimit.setStartDate(Util.getFirstDateOfCurrentMonth());
		customerApiLimit.setEndDate(Util.getLastDateOfCurrentMonth());
		return customerApiLimit;
	}
	
	public CustomerApiLimitTo buildMockCustomerApiLimitTo() {
		CustomerApiLimitTo customerApiLimitTo = new CustomerApiLimitTo();
		customerApiLimitTo.setId(1L);
		customerApiLimitTo.setCurrentAccessCount(2000L);
		customerApiLimitTo.setCurrentAccessLimit(4000L);
		customerApiLimitTo.setCustomerId(1L);
		customerApiLimitTo.setStartDate(Util.getFirstDateOfCurrentMonth());
		customerApiLimitTo.setEndDate(Util.getLastDateOfCurrentMonth());
		return customerApiLimitTo;
	}
	
	public AdminUser buildMockAdminUser(){
		AdminUser admin = new AdminUser();
		admin.setId(1L);
		admin.setIsActive(true);
		admin.setLoginId("junit" + new Date().getTime());
		String becryptedPwd = Util.getBCrptPassword("MenloTechnologies@123");
		admin.setPassword(becryptedPwd.getBytes());
		admin.setPasswordStr("MenloTechnologies@123");
		admin.setName("JUNIT");
		admin.setAccessLevel(5);
		return admin;
	}
	
	public ReportLogCriteria buildMockReportLogCriteria(){
		ReportLogCriteria   reportLogCriteria= new ReportLogCriteria();
		reportLogCriteria.setPageNumber(1);
		reportLogCriteria.setPageSize(10);
		reportLogCriteria.setToDateStr("15-2-16");
		reportLogCriteria.setFromDateStr("14-2-16");
		return reportLogCriteria;
	}
	public ApiRequest buildMockApiRequest(){
		ApiRequest   apiRequest= new ApiRequest();
		apiRequest.setApiKey("menlo");
		apiRequest.setCustProdId(1L);
		apiRequest.setAttribute1("Junit");
		apiRequest.setEndpoint("equailar");
		apiRequest.setIp4("198.10.10.1");
		apiRequest.setIp6("198.15.1.14");
		apiRequest.setResponseCode(new Long(200));
		apiRequest.setTimeStamp(new Date());
		apiRequest.setTimeStampStr("15-2-16");
		return apiRequest;
	}
	public SortingCriteria buildMockSortingCriteria(String sortingfield){
		SortingCriteria   sortingCriteria= new SortingCriteria();
		sortingCriteria.setPageNumber(2);
		sortingCriteria.setPageSize(10);
		sortingCriteria.setSortingField(sortingfield);
		sortingCriteria.setSortOrder("ASC");
		return sortingCriteria;
		
	}

	public AdminAction buildMockAdminAction(){
		AdminAction  adminAction= new AdminAction();
		adminAction.setId(1L);
		adminAction.setAdminId(2L);
		adminAction.setCustomerId(3L);
		adminAction.setIp4("198.26.2.19");
		adminAction.setIp6("198.125.2.3");
		adminAction.setDescription("Menlo");
		adminAction.setTimeStamp(new Date());
		return adminAction;
		
		
	}

	
	
}
