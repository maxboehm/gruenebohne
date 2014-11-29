package com.gruenebohne.beans.url;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

public abstract class ImageBean {

	protected abstract byte[] getPicture(int nID);

	private int prodID;

	/**
	 * Get Picture from database and serve it to the customer
	 * THE CODE WAS MOSTLY TAKEN FROM THE INTERNET
	 * @throws IOException
	 */
	public void render() throws IOException {
		// get HttpServletResponse
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

		byte[] picture = getPicture(getProdID());

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

}
