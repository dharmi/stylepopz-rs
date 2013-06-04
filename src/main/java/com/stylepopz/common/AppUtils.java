package com.stylepopz.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.stylepopz.model.IntermediatePreference;
import com.stylepopz.model.Preference;
import com.stylepopz.model.Sizes;
import com.stylepopz.model.entity.PreferenceAsJson;

public class AppUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(AppUtils.class);
	
	public static Preference intermediateToPref(IntermediatePreference pref, Preference existingPref) {

		if(existingPref != null){
			// existing preference, so update
			Sizes sizes = existingPref.getSizes();
			
			
			Sizes oSizes = existingPref.getSizes();
			Iterator<Map<String, String>> oIterator = null;
			
			//shirts
			List<Map<String, String>> oShirts = oSizes.getShirts();
			
			List<String> nShirts = pref.getShirts();
			Iterator<String> tmpIterator = nShirts.iterator();
			while(tmpIterator.hasNext()){
				String nShirt = tmpIterator.next();
				
				oIterator = oShirts.iterator();
				boolean flg = false;
				while(oIterator.hasNext()){
					Map<String, String> oMap = oIterator.next();
					if(oMap.containsValue(nShirt)){
						flg = true;
						break;
					}
				
				}
				if(!flg){
					Map<String, String> _oMap = new HashMap<String, String>();
					_oMap.put("selected", "N"); 
					_oMap.put("url", "tbd.png");
					_oMap.put("shirt", nShirt);
					existingPref.getSizes().getShirts().add(_oMap);
				}

			}
			
			oIterator = oShirts.iterator();
			while(oIterator.hasNext()){
				Map<String, String> oMap = oIterator.next();
				if(nShirts.contains(oMap.get("shirt"))){
					//logger.info(oMap.get("shirt")+" present with selected="+oMap.get("selected"));
					String selected = oMap.get("selected")+"";
					if(selected.equals("N")) 
						oMap.put("selected", "Y");
					else
						oMap.put("selected", "N");
					//(oShirts.get(oShirts.indexOf("shirt"))).put("shirt", "");
					//logger.info("after : "+oMap.get("shirt")+" present with selected="+oMap.get("selected"));
					sizes.setShirts(oShirts);
				}else{
					logger.info(oMap.get("shirt")+" not present");
				}
			}
			
			//pants
			List<Map<String, String>> oPants = oSizes.getPants();
			
			List<String> nPants = pref.getPants();
			tmpIterator = nPants.iterator();
			while(tmpIterator.hasNext()){
				String nPant = tmpIterator.next();
				
				oIterator = oPants.iterator();
				boolean flg = false;
				while(oIterator.hasNext()){
					Map<String, String> oMap = oIterator.next();
					if(oMap.containsValue(nPant)){
						flg = true;
						break;
					}
				
				}
				if(!flg){
					Map<String, String> _oMap = new HashMap<String, String>();
					_oMap.put("selected", "Y"); 
					_oMap.put("url", "tbd.png");
					_oMap.put("pant", nPant);
					existingPref.getSizes().getPants().add(_oMap);
				}

			}
			
			oIterator = oPants.iterator();
			while(oIterator.hasNext()){
				Map<String, String> oMap = oIterator.next();
				if(nPants.contains(oMap.get("pant"))){
					//logger.info(oMap.get("shirt")+" present with selected="+oMap.get("selected"));
					String selected = oMap.get("selected")+"";
					if(selected.equals("N")) 
						oMap.put("selected", "Y");
					else
						oMap.put("selected", "N");
					//(oShirts.get(oShirts.indexOf("shirt"))).put("shirt", "");
					//logger.info("after : "+oMap.get("shirt")+" present with selected="+oMap.get("selected"));
					sizes.setPants(oPants);
				}else{
					logger.info(oMap.get("pant")+" not present");
				}
			}
			
			//shoes
			List<Map<String, String>> oShoes = oSizes.getShoes();

			List<String> nShoes = pref.getShoes();
			tmpIterator = nShoes.iterator();
			while(tmpIterator.hasNext()){
				String nShoe = tmpIterator.next();
				
				oIterator = oShoes.iterator();
				boolean flg = false;
				while(oIterator.hasNext()){
					Map<String, String> oMap = oIterator.next();
					if(oMap.containsValue(nShoe)){
						flg = true;
						break;
					}
				
				}
				if(!flg){
					Map<String, String> _oMap = new HashMap<String, String>();
					_oMap.put("selected", "Y"); 
					_oMap.put("url", "tbd.png");
					_oMap.put("shoe", nShoe);
					existingPref.getSizes().getShoes().add(_oMap);
				}

			}

			oIterator = oShoes.iterator();
			while(oIterator.hasNext()){
				Map<String, String> oMap = oIterator.next();
				if(nShoes.contains(oMap.get("shoe"))){
					String selected = oMap.get("selected")+"";
					if(selected.equals("N")) 
						oMap.put("selected", "Y");
					else
						oMap.put("selected", "N");
					sizes.setShoes(oShoes);
				}else{
					logger.info(oMap.get("shoe")+" not present");
				}
			}
			
			
			return existingPref;
		}else{
			// its a new preference, create one.
			Preference newPref = new Preference();
			newPref.setId(pref.getId());
			newPref.setSex(pref.getSex()); 

			Sizes oSizes = new Sizes();

			// shirts
			List<Map<String, String>> oLst = new ArrayList<Map<String, String>>();
			List<String> iCategory = pref.getShirts();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "Y"); 
					oMap.put("url", "tbd.png");
					oMap.put("shirt", item);
					oLst.add(oMap);
				}
			}
			oSizes.setShirts(oLst);

			// pants
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getPants();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "Y"); 
					oMap.put("url", "tbd.png");
					oMap.put("pant", item);
					oLst.add(oMap);
				}
			}
			oSizes.setPants(oLst);

			// shoes
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getShoes();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "Y"); 
					oMap.put("url", "tbd.png");
					oMap.put("shoe", item);
					oLst.add(oMap);
				}
			}
			oSizes.setShoes(oLst);

			// set sizes
			newPref.setSizes(oSizes);

			// colors
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getColors();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "Y"); 
					oMap.put("url", "tbd.png");
					oMap.put("color", item);
					oLst.add(oMap);
				}
			}
			newPref.setColors(oLst);

			// prints
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getPrints();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "Y"); 
					oMap.put("url", "tbd.png");
					oMap.put("print", item);
					oLst.add(oMap);
				}
			}
			newPref.setPrints(oLst);

			// luxuryBrands
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getLuxuryBrands();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "Y"); 
					oMap.put("url", "tbd.png");
					oMap.put("luxbrand", item);
					oLst.add(oMap);
				}
			}
			newPref.setLuxuryBrands(oLst);

			// hiStreetBrands
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getHiStreetBrands();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "Y"); 
					oMap.put("url", "tbd.png");
					oMap.put("hibrand", item);
					oLst.add(oMap);
				}
			}
			newPref.setHiStreetBrands(oLst);

			// fastFashionBrands
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getFastFashionBrands();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "Y"); 
					oMap.put("url", "tbd.png");
					oMap.put("fastfash", item);
					oLst.add(oMap);
				}
			}
			newPref.setFastFashionBrands(oLst);

			// indieDesigners
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getIndieDesigners();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "Y"); 
					oMap.put("url", "tbd.png");
					oMap.put("indie", item);
					oLst.add(oMap);
				}
			}
			newPref.setIndieDesigners(oLst);

			// bloggerPreferences
			oLst = new ArrayList<Map<String, String>>();
			iCategory = pref.getBloggerPreferences();
			if(iCategory != null){
				Iterator<String> iIterator = iCategory.iterator();
				while(iIterator.hasNext()){
					String item = iIterator.next();
					Map<String, String> oMap = new HashMap<String, String>();
					oMap.put("selected", "Y"); 
					oMap.put("url", "tbd.png");
					oMap.put("blogger", item);
					oLst.add(oMap);
				}
			}
			newPref.setBloggerPreferences(oLst);

			return newPref;
		}
	}

	public static Preference getPreferenceFromJson(String prefJson) {
		return (new Gson()).fromJson(prefJson, Preference.class);
		/*IntermediatePreference pref = (new Gson()).fromJson(prefJson, IntermediatePreference.class);
		return intermediateToPref(pref, null);*/
	}

	public static Preference getDefaultPreference(String profileId) {
		String preferenceJson = "{\"id\":\""+profileId+"\",\"sex\":\"M\",\"colors\":[{\"selected\":\"N\",\"color\":\"skyblue\",\"url\":\"tbd.png\"},{\"selected\":\"N\",\"color\":\"red\",\"url\":\"tbd.png\"}],\"prints\":[{\"selected\":\"N\",\"print\":\"skyblue\",\"url\":\"tbd.png\"},{\"selected\":\"N\",\"print\":\"purple\",\"url\":\"tbd.png\"}],\"luxuryBrands\":[{\"selected\":\"N\",\"luxbrand\":\"Armani\",\"url\":\"tbd.png\"},{\"selected\":\"N\",\"luxbrand\":\"Gucci\",\"url\":\"tbd.png\"}],\"hiStreetBrands\":[{\"selected\":\"N\",\"hibrand\":\"brand1\",\"url\":\"tbd.png\"},{\"selected\":\"N\",\"hibrand\":\"brand2\",\"url\":\"tbd.png\"}],\"fastFashionBrands\":[{\"selected\":\"N\",\"url\":\"tbd.png\",\"fastfash\":\"brand1\"},{\"selected\":\"N\",\"url\":\"tbd.png\",\"fastfash\":\"brand2\"}],\"indieDesigners\":[{\"selected\":\"N\",\"indie\":\"sonas jeans\",\"url\":\"tbd.png\"}],\"bloggerPreferences\":[],\"sizes\":{\"shirts\":[{\"selected\":\"N\",\"url\":\"tbd.png\",\"shirt\":\"s\"},{\"selected\":\"N\",\"url\":\"tbd.png\",\"shirt\":\"m\"},{\"selected\":\"N\",\"url\":\"tbd.png\",\"shirt\":\"l\"}],\"pants\":[{\"pant\":\"s\",\"selected\":\"N\",\"url\":\"tbd.png\"},{\"pant\":\"m\",\"selected\":\"N\",\"url\":\"tbd.png\"},{\"pant\":\"l\",\"selected\":\"N\",\"url\":\"tbd.png\"}],\"shoes\":[{\"selected\":\"N\",\"shoe\":\"18\",\"url\":\"tbd.png\"},]}}";
		return (new Gson()).fromJson(preferenceJson, Preference.class);
	}

}
