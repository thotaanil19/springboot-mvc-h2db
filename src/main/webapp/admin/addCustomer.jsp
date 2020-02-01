	<div class="row large-12 medium-12 small-12 edit-customer-loading hide"><img class="loading-track-icon" src="/data-api/images/loading.gif"><span class="loading-span">Please wait...</span></div>
	<div class="row large-12 medium-12 small-12 ">
		<div class="large-6 medium-6 small-12 columns">
			<div class="add-customer-text error-append marginTop10" id="addCustomer">Add Customer</div>
			<div class="row addCustomer-textfield customer-fields">
				<div class="hide customer-space-msz">Customer ID should not contain spaces.</div>
				<div class="marginTop20">
					<span>Login ID:</span>
					<input type="text" value="" placeholder="Customer Login ID" class="customer-login-id" maxlength="20" />
				</div>
				<div>
					<span>Customer Password:</span>
					<div class="pwd-rules marginLeft20 pwd-position">
					 	Passwords should require a minimum of 12 characters and at least 3 of the following</br>
						1.An upper case letter 2.A lower case letter 3.A digit 4.Any of the following !$%@#*_
					</div>
					<input type="password" value="" placeholder="Customer password" class="customer-password" maxlength="60" />
					 	
				</div>
				<div>
					<span>Customer Email:</span>
					<input type="text" value="" placeholder="Customer Email" class="customer-email"/>
				</div>
				<div>
					<span>Customer Company:</span>
					<input type="text" value="" placeholder="Company Name" class="customer-company" maxlength="40" />
				</div>
				<div>
					<span>Monthly Base API hit limit:</span>
					<input type="text" value="" placeholder="base-access-limit" class="customer-base-limit"/>
				</div>
				<div>
					<span>API hit counter reset day of month:</span>
					<input type="text" class="customer-reset-month" value="" placeholder="API hit Reset Date" />
					<div class="reset-date-error hide">Reset date to be between 1-25</div>
				</div>
				<div class="customer-active hide">
					<span>Is Active:</span>
					<select class="customer-is-active"></select>
				</div>
			</div>
		</div>
		<div id="change-pwd" class="changeCustomerPwd large-6 medium-6 small-12 columns hide">
				Change Password
		</div>
		<div class="large-6 medium-6 small-12 columns change-pwd-text hide" id="change-pwd-div">
		
			<!-- <div class="admin-details text-center">
				Change Password	
			</div> -->
			 <div class="pwd-success hide text-center"></div>	
			 <div class="pwd-error error-msz hide">
			 	Passwords should require a minimum of 12 characters and at least 3 of the following</br>
				1.An upper case letter 2.A lower case letter 3.A digit 4.Any of the following !$%@#*_
			 </div>	
			 <div class="pwd-rules marginLeft20">
			 	Passwords should require a minimum of 12 characters and at least 3 of the following</br>
				1.An upper case letter 2.A lower case letter 3.A digit 4.Any of the following !$%@#*_
			</div>	
			<div class= "change-pwd-details">
				<div class="password-div">
					<input type="password" placeholder="Password" name="password" id="password-new" class="text-field" maxlength="60"  / >
					<img src="/data-api/images/password-icon.png">
				</div>
				<div class="password-div">
					<input type="password" placeholder="Confirm Password" name="confirmpassword" id="cnfrm-password" class="text-field" maxlength="60"  />
					<img src="/data-api/images/password-icon.png">
				</div>
			</div>
			<div class="buttons-div right">
				<input type="button" value="Save" class="update-button" id="pwd-update-button">
				<input type="button" value="Cancel" class="cancel-button" id="pwd-cancel-button">
			</div>
		</div>
	</div>
	
	<div class="row large-12 medium-12 small-12 add-customer-text marginTop10">Add Customer Products</div>
	<div class="row large-12 medium-12 small-12 add-products-text">
		<div class="productBorder marginTop10">
			<img src="/data-api/images/add-icon.png" id="add-product-icon">
			<span class="paddingLeft6">Add Products</span>
		</div>
		<div class="product-list marginTop10 large-2 medium-4 small-8 hide">
			<select id="product-select">
			</select>
		</div>
		<div class="all-products hide marginTop10">
			<div class="product-label"></div>
		 </div>
	</div>
	<div class="row large-12 medium-12 small-12 add-customer-api ">
		<div class="api-text display-block width450 marginTop20">
			Add Customer API Keys
		</div>
		<div class="api-text display-block marginTop20">
			<img src="/data-api/images/add-icon.png" id="api-key-icon">
			<span class="paddingLeft6">Generate New API Key</span>
		</div>
	</div>
	<div class="row large-12 medium-12 small-12 ">
		<div class="api-grid hide marginTop10">
			<div class="small-12 large-1 medium-1 columns text">Select</div>
			<div class="small-12 large-6 medium-6 columns text ">API Key ID</div>
			<div class="small-12 large-2 medium-2 columns text">Active</div>
			<div class="small-12 large-3 medium-3 columns text">Disabled Time</div>
		</div>
		<div class="api-key-details marginTop10">
		</div>
  	</div>
  	<div class="row large-12 medium-12 small-12">
			<div class="large-7 medium-7 small-8 marginTop20">
				<input type="button" id="customer-cancel-button" class="cancel-button right marginLeft20" value="Cancel">
				<span class="hint-text"> Saves customer details,Customer products, API Keys</span>
				<input type="button" id="customer-add-button" class="add-button right" value="Add">
				<input type="button" id="customer-update-button" class="add-button right hide" value="Save and Exit">
			</div>
	</div>
	<hr>
  	<div class="row customer-api-limit hide">
  		<div class=" large-12 medium-12 small-12 add-customer-text marginTop20 ">
  			Monthly API limit
  		</div>
  	</div>
  	<div class="row large-12 medium-12 small-12  add-api-date customer-api-limit hide">
		<div class="large-2 medium-3 small-4 columns marginTop20">
			<input id="startDate" type="text" placeholder="Start Date" readonly/>
			<img   id="dp4" src="/data-api/images/full-calender-icon.png">
		</div>
		<div class="large-2 medium-3 small-4 columns marginLeft40 marginTop20">
			<input id="endDate" type="text" placeholder="End Date" readonly/>
			<img   id="dp5"  src="/data-api/images/full-calender-icon.png">
		</div>
	</div>
	<div class="row addCustomer-textfield success-api-msz customer-api-limit hide" style="margin: 0 auto;">
		<div class="width240 hide marginTop20">
			<input type="text" value="" placeholder="Customer Access Limit" class="customer-access-limit"/>
		</div>
	</div>
	<div class="row large-12 medium-12 small-12 customer-api-limit hide">
			<div class="large-7 medium-7 small-8 marginTop10">
				<input type="button" id="api-cancel-button" class="cancel-button right marginLeft20" value="Cancel">
				<span class="hint-text"> Saves Monthly API Limit</span>
				<input type="button" id="api-update-button" class="add-button right" value="Save">
			</div>
	</div>
	