package org.haohhxx.util.sta;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haozhenyuan
 * 计算信息熵
 */
public class WordEntropy {

    private HashMap<String,Double> leftEntropyMap = new HashMap<>();
    private HashMap<String,Double> rightEntropyMap = new HashMap<>();

    public void calculateWordEntropy(List<String> words){
        Multimap<String,Integer> indexMap = ArrayListMultimap.create();
        for (int i = 0; i <words.size(); i++) {
            indexMap.put(words.get(i),i);
        }
        for(String word:indexMap.keySet()){
            Collection<Integer> is = indexMap.get(word);
            int times = is.size();
            Map<String,Integer> leftStaMap = Maps.newHashMap();
            Map<String,Integer> rightStaMap = Maps.newHashMap();
            for(Integer i:is){
                String wordLeft,wordRight;
                if(i==0){
                    wordLeft = "__EOS_L__";
                    wordRight = words.get(i+1);
                }else if((i+1)==words.size()){
                    wordLeft = words.get(i-1);
                    wordRight = "__EOS_R__";
                }else{
                    wordLeft = words.get(i-1);
                    wordRight = words.get(i+1);
                }
                Integer leftNub = leftStaMap.getOrDefault(wordLeft,0)+1;
                Integer rightNub = rightStaMap.getOrDefault(wordRight,0)+1;
                leftStaMap.put(wordLeft,leftNub);
                rightStaMap.put(wordRight,rightNub);
            }
            double leftEntropy = entropy(times,leftStaMap.values());
            double rightEntropy = entropy(times,rightStaMap.values());
            leftEntropyMap.put(word,leftEntropy);
            rightEntropyMap.put(word,rightEntropy);
        }
    }

    private double entropy(double times, Collection<Integer> nubs){
        double hw=0.0;
        for(Integer nub:nubs){
            double p = nub.doubleValue()/times;
            hw -= (p*Math.log(p));
        }
        return hw;
    }

    public HashMap<String, Double> getLeftEntropyMap() {
        return leftEntropyMap;
    }

    public HashMap<String, Double> getRightEntropyMap() {
        return rightEntropyMap;
    }

    public void clear(){
        leftEntropyMap.clear();
        rightEntropyMap.clear();
    }

}
