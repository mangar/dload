#DLoad

### Why it's useful?

DLoad is the short mention for Dynamically Load. 
This project was developed to let boring tasks imposed to developer behind. <br>

Tasks that aggregate absolutely nothing interesting and cool to our jobs. Instead of that it just force the developer to implement more dummy code just because some framework told so. <br>

### Functions: <br>

  - Get/Set automatically generation

----------

# Get/Set automatically generation

**Why it's useful?**

Note that the fields are set up with a public view. This is because the getters and setters will be available just in runtime. 
If you need to get access to these fields while you're coding your app get access direct to the field, instead of using a get/set method.

It' useful specially when you are using frameworks that force you to implements getters and setters for every field, such as: Struts2 and JSF.


## Usage

### Web Application

To use it on a web app I suggest you to create a Servlet with the init() filled out. Something like this:


	@SuppressWarnings("serial")
	public class AssistEnhancerServlet extends HttpServlet {
	
	    @Override
	    public void init() throws ServletException {
	        super.init();
	        try {
	            GetSetAssist.createWeb().doEnhance();
	        } catch (Exception e) {
	            System.err.println("Problems: " + e.toString());
	        }
	    }
	
	}

And define it on the web.xml:

	<servlet>
		<servlet-name>AssistEnhancerServlet</servlet-name>
		<servlet-class>mng.dload.samples.servlet.AssistEnhancerServlet</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>



### Stand alone app

Simply call it right in your very first method (before loading enhanced class):

	GetSetAssist.create().doEnhance();




#### Steps

 1. Step 1:  <br> Get some VO annotated and let fields as public 

    @GetSet
    public class PromotionRecordForm  {

        public String id;
        public String referenceCode;
        public String startDate;
        public String endDate;
        public String factor;
        public String productCode;

    }


Note that the fields are setted up with a public view. This is because the getters and setters will be available just in runtime. 
If you need to get access to these fields while you're coding your app get access direct to the field, instead of using a get/set method.
 


 

----------


# Copyright
Copyright © 2010 Marcio Mangar, released under the MIT license
