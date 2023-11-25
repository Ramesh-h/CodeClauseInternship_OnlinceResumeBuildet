package com.resume.builder;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

@Service
public class PdfGenerationService {

	public byte[] generatePdf(Person person) throws IOException, DocumentException {
		String htmlContent = generateHtmlFromPerson(person);
		return generatePdfFromHtml(htmlContent);
	}

	private String generateHtmlFromPerson(Person person) {

		return "<html>" +
	            "<head>" +
	            "    <style>" +
	            "        body {" +
	            "            font-family: 'Arial', sans-serif;" +
	            "            background-color: #f4f4f4;" +
	            "            margin: 0;" +
	            "            padding: 0;" +
	            "            display: flex;" +
	            "            align-items: center;" +
	            "            justify-content: center;" +
	            "            height: 150vh;" +
	            "        }" +
	            "        .resume-container {" +
	            "            background-color: #fff;" +
	            "            padding: 20px;" +
	            "            border-radius: 8px;" +
	            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);" +
	            "            box-sizing: border-box;" +
	            "            width: 800px;" +
	            "        }" +
	            "        h1 {" +
	            "            color: #333;" +
	            "            text-align: center;" +
	            "        }" +
	            "        label {" +
	            "            display: block;" +
	            "            margin-bottom: 8px;" +
	            "            font-weight: bold;" +
	            "        }" +
//	            "        p {" +
//	            "            margin-bottom: 16px;" +
//	            "        }" +
	            "        ul {" +
	            "            list-style-type: none;" +
	            "            padding: 0;" +
	            "        }" +
	            "        li {" +
	            "            margin-bottom: 8px;" +
	            "        }" +
	            "    </style>" +
	            "</head>" +
	            "<body>" +
	            "    <div class=\"resume-container\">" +
	            "        <h1>Resume </h1>" +
	            "        <label>Name:    "  + person.getFirstName() + " " + person.getLastName() + "</label>" +
	            "        <label>Email:   " + person.getEmail() +"</label> " +
	            "        <label>Phone:   " + person.getPhone() + " </label> " +
	            "        <label>Education: "+ person.getEducation() + "</label> " +
	            "        <label>Experience: " + person.getExperience() + "</label> " +
	            "        <label>Skills: </label> " +
	                 "        <ul>" +
	                 "            <li> " + String.join(" </li> <li> ", person.getSkills()) + "</li>" +
	                 "        </ul>" +
	            "        <label>Address: " + person.getAddress() + "</label> " +
	            "        <label>City: "+ person.getCity() +"</label> " +
	            "        <label>State: "+ person.getCity() +"</label> " +
	            "        <label>Zipcode: "+ person.getZipcode() +"</label>" +
	            "    </div>" +
	            "</body>" +
	            "</html>";
	}
	
//	public byte[] generatePdf(Person person) throws IOException, DocumentException {
//        String htmlContent = loadHtmlTemplate("resume-template.html");
//        // Replace placeholders in the template with actual data from the Person object
//        htmlContent = htmlContent.replace("${person.firstName}", person.getFirstName())
//                                .replace("${person.lastName}", person.getLastName())
//                                .replace("${person.email}", person.getEmail())
//                                .replace("${person.phone}", person.getPhone())
//                                .replace("${person.education}", person.getEducation())
//                                .replace("${person.experience}", person.getExperience())
//                                // Add more replacements for other fields
//                                .replace("${person.skills}", String.join(", ", person.getSkills()))
//                                .replace("${person.address}", person.getAddress())
//                                .replace("${person.city}", person.getCity())
//                                .replace("${person.state}", person.getState())
//                                .replace("${person.zipcode}", person.getZipcode());
//
//        return generatePdfFromHtml(htmlContent);
//    }

    private String loadHtmlTemplate(String templateName) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/" + "resumetemplate.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }

	public byte[] generatePdfFromHtml(String htmlContent) throws IOException, DocumentException {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(htmlContent);
			renderer.layout();
			renderer.createPDF(outputStream);
			return outputStream.toByteArray();
		}
	}
}
