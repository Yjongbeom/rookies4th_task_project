package mylab.book.control;
import mylab.book.entity.*;
import java.util.Map;
import java.util.HashMap;
import java.text.DecimalFormat;

public class StatisticsAnalyzer {
	
    public Map<String, Double> calculateAveragePriceByType(Publication[] publications) {
        Map<String, Integer> countMap = new HashMap<>();
        Map<String, Double> sumMap = new HashMap<>();
        
        for (Publication pub : publications) {
            String type = getPublicationType(pub);
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
            sumMap.put(type, sumMap.getOrDefault(type, 0.0) + pub.getPrice());
        }
        
        Map<String, Double> result = new HashMap<>();
        for (String type : sumMap.keySet()) {
            double avg = sumMap.get(type) / countMap.get(type);
            result.put(type, avg);
        }
        
        return result;
    }
    
    public Map<String, Double> calculatePublicationDistribution(Publication[] publications) {
        Map<String, Integer> countMap = new HashMap<>();
        
        for (Publication pub : publications) {
            String type = getPublicationType(pub);
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }
        
        Map<String, Double> result = new HashMap<>();
        for (String type : countMap.keySet()) {
            double percentage = (double) countMap.get(type) / publications.length * 100;
            result.put(type, percentage);
        }
        
        return result;
    }
    
    public double calculatePublicationRatioByYear(Publication[] publications, String year) {
        int count = 0;
        for (Publication pub : publications) {
            if (pub.getPublishedDate().startsWith(year)) {
                count++;
            }
        }
        return (double) count / publications.length * 100;
    }
    
    private String getPublicationType(Publication pub) {
        if (pub instanceof Novel) {
            return "소설";
        } else if (pub instanceof Magazine) {
            return "잡지";
        } else if (pub instanceof ReferenceBook) {
            return "참고서";
        }
        return "기타";
    }
    
    public void printStatistics(Publication[] publications) {
        DecimalFormat df = new DecimalFormat("#,###.##");
        DecimalFormat priceFormat = new DecimalFormat("#,###");
        
        Map<String, Double> avgPriceMap = calculateAveragePriceByType(publications);
        System.out.println("1. 타입별 평균 가격:");
        for (Map.Entry<String, Double> entry : avgPriceMap.entrySet()) {
            System.out.println("   - " + entry.getKey() + ": " + 
                              priceFormat.format(entry.getValue()) + "원");
        }
        
        Map<String, Double> distributionMap = calculatePublicationDistribution(publications);
        System.out.println("\n2. 출판물 유형 분포:");
        for (Map.Entry<String, Double> entry : distributionMap.entrySet()) {
            System.out.println("   - " + entry.getKey() + ": " + 
                              df.format(entry.getValue()) + "%");
        }
        
        double ratio2007 = calculatePublicationRatioByYear(publications, "2007");
        System.out.println("\n3. 2007년에 출판된 출판물 비율: " + 
                          df.format(ratio2007) + "%");
    }
}