package com.gruenebohne.beans.url;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.gruenebohne.EJB.ProductBaseEJB;

@ManagedBean(name = "image")
@RequestScoped
public class ImageBean {

	@EJB
	private ProductBaseEJB productbasejb;

	private int prodID;

	public void render() throws IOException {
		// get HttpServletResponse
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

		byte[] picture = productbasejb.getProduct(getProdID()).getPicture();

		// set correct content type
		response.setContentType("image/jpg");
		response.setContentLength(picture.length);
		response.setHeader("Content-Disposition", "inline; filename=\"" + getProdID()+ ".jpg\"");

		OutputStream os = null;
		ByteArrayOutputStream baos = null;

		try{

			// create new output stream
			os = response.getOutputStream();

			// create new ByteArrayOutputStream
			baos = new ByteArrayOutputStream();

			// write buffer to the byte array output stream
			baos.write(picture);

			// write to the output stream
			baos.writeTo(os);

		}catch(Exception e){

			// if I/O error occurs
			e.printStackTrace();
		}finally{
			if(baos!=null)
				baos.close();
			if(os!=null)
				os.close();
		}
		// mark response as completed
		context.responseComplete();
	}


	public int getProdID() {
		return prodID;
	}

	public void setProdID(int prodID) {
		this.prodID = prodID;
	}

	//		try {
	//			// open image
	//			picture = Files.readAllBytes(Paths.get("/Users/Max/Documents/img.jpg"));
	//
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}


	//		FileInputStream fis = new FileInputStream("/Users/Max/Documents/img.jpg");
	//		OutputStream out = response.getOutputStream();
	//		int c;
	//		while ((c = fis.read()) != -1) {
	//			out.write(c);
	//		}
	//		fis.close();
	//		context.responseComplete();
	//
	//		if(true)return;

}
