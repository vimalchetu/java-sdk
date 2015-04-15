package com.velocity.services;

import java.io.FileInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.velocity.model.response.BankcardTransactionResponsePro;

public class MyTests {

	public static void main(String[] args) {
		
		JAXBContext jc = null;
		try {
			
			//Create the filter (to add namespace) and set the xmlReader as its parent.
			/*NamespaceFilter inFilter = new NamespaceFilter("http://www.example.com/namespaceurl", true);
			XMLReader reader = XMLReaderFactory.createXMLReader();
			inFilter.setParent(reader);

			//Prepare the input, in this case a java.io.File (output)
			InputSource is = new InputSource(new FileInputStream("D:\\JARS\\BankcardTransactionResponsePro.xml"));

			//Create a SAXSource specifying the filter
			SAXSource source = new SAXSource(inFilter, is);

			//Do unmarshalling
			Object myJaxbObject = u.unmarshal(source); */
			
			jc = JAXBContext.newInstance(BankcardTransactionResponsePro.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller(); 
			jc = JAXBContext.newInstance(BankcardTransactionResponsePro.class);
			
			BankcardTransactionResponsePro response = (BankcardTransactionResponsePro) unmarshaller.unmarshal(new FileInputStream("D:\\JARS\\BankcardTransactionResponsePro.xml"));
			System.out.println("date:: "+response.getServiceTransactionDateTime().getDate());
			System.out.println("time:: "+response.getServiceTransactionDateTime().getTime());
			System.out.println("time zone:: "+response.getServiceTransactionDateTime().getTimeZone());
			System.out.println(response.getStatus());
			System.out.println(response.getStatusCode());
			System.out.println(response.getServiceTransactionId());
			System.out.println(response.getCaptureState());
			System.out.println(response.getTransactionState());
			System.out.println(response.isAcknowledged());
			System.out.println("PrepaidCard::"+response.getPrepaidCard());
			System.out.println(response.getReference());
			System.out.println(response.getAmount());
			System.out.println(response.getCardType());
			System.out.println(response.getFeeAmount());
			System.out.println(response.getApprovalCode());
			System.out.println(response.getAvsResult());
			System.out.println(response.getBatchId());
			System.out.println(response.getcVResult());
			System.out.println(response.getCardLevel());
			System.out.println(response.getDowngradeCode());
			System.out.println(response.getMaskedPAN());
			System.out.println(response.getPaymentAccountDataToken());
			System.out.println(response.getRetrievalReferenceNumber());
			System.out.println(response.getAdviceResponse());
			System.out.println(response.getCommercialCardResponse());
			System.out.println(response.getReturnedACI());
			
			
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

	}
	
	/*public static String RemoveAllXmlNamespace(String xmlData)
    {
        String xmlnsPattern = "\\s+xmlns\\s*(:\\w)?\\s*=\\s*\\\"(?<url>[^\\\"]*)\\\"";
        
        Pattern r = Pattern.compile(xmlnsPattern);
        
        Matcher m = r.matcher(xmlData);
        
        if (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
         } else {
            System.out.println("NO MATCH");
         }
        
        MatchCollection matchCol = Regex.matches(xmlData, xmlnsPattern);

        foreach (Match m in matchCol)
        {
            xmlData = xmlData.Replace(m.ToString(), "");
        }
        return xmlData;
    }*/
}


