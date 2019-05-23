package main;


import java.util.ArrayList;
import java.util.List;

/* This file is copyright (c) 2008-2012 Philippe Fournier-Viger
* 
* This file is part of the SPMF DATA MINING SOFTWARE
* (http://www.philippe-fournier-viger.com/spmf).
* 
* SPMF is free software: you can redistribute it and/or modify it under the
* terms of the GNU General Public License as published by the Free Software
* Foundation, either version 3 of the License, or (at your option) any later
* version.
* SPMF is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
* A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* You should have received a copy of the GNU General Public License along with
* SPMF. If not, see <http://www.gnu.org/licenses/>.
*/
/**
* This class represents a cluster found by a clustering algorithm such as DBScan.
* A cluster is a list of vectors of doubles.
* 
*  @see Email
*  @see AlgoDBSCAN
 * @author Philippe Fournier-Viger
 */
public class Cluster {

	protected List<Email> vectors = new ArrayList<Email>();
	
	public Cluster() {
		super();
	}

	/**
	 * Add a vector of doubles to this cluster.
	 * @param vector The vector of doubles to be added.
	 */
	public void addVector(Email vector) {
		vectors.add(vector);
	}

	/**
	 * Return a string representing this cluster.
	 */
	public String toString() {
		
		StringBuilder buffer = new StringBuilder();
		if(vectors.size() >=1){
                    buffer.append("size:" + vectors.size()+"\n");
			for(Email email : vectors){
				buffer.append("<");
                buffer.append(email.getId());
                buffer.append(":");
                buffer.append(email.getTopic());
                buffer.append("-");
                //buffer.append(email.getSentiments().toString());
                buffer.append(email.getSentimentKeywords().toString());
                
                //Compute sentiment polarity
                int polarityScore = 0;
                for(int i = 0; i < email.getSentimentKeywords().size(); i++){
                	
                    //transfer sentiment key word into positive of negative class label
                	String keyWord = email.getSentimentKeywords().get(i);
                	if(keyWord.startsWith("-")){
                        polarityScore += -1;
                    } else {
                        polarityScore += 1;
                    }
                }                   
                if (polarityScore >= 2){
                	buffer.append(": SP");
                }else if (polarityScore >=1 & polarityScore <2){
                	buffer.append(": P");
                }else if (polarityScore == 0){
                	buffer.append(": NEUTRAL");
                }else if (polarityScore >=-1 & polarityScore <0){
                	buffer.append(": N");
                }else{
                	buffer.append(": SN");
                }
				buffer.append(">\n");
			}
		}
		return buffer.toString();
	}

	/**
	 * Method to get the vectors in this cluster
	 * @return the vectors.
	 */
	public List<Email> getVectors() {
		return vectors;
	}

	/**
	 * Method to remove a vector from this cluster and update
	 * the internal sum of vectors at the same time.
	 * @param vector  the vector to be removed
	 */
	public void remove(Email vector) {
		vectors.remove(vector);		
	}

	/**
	 * Method to remove a vector from this cluster without updating internal
	 * structures.
	 * @param vector  the vector to be removed
	 */
	public void removeVector(Email vector) {
		vectors.add(vector);
	}

	/**
	 * Check if a vector is contained in this cluster.
	 * @param vector A vector of doubles
	 * @return true if the vector is contained in this cluster.
	 */
	public boolean contains(Email vector) {
		return vectors.contains(vector);
	}

}