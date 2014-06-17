package automaticResumeParser.utilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class TextAnalysisAlgorithms {
	public static void main(String args[]) {

		String a1 = "In mathematics, the absolute value (or modulus) |x| of a real number x is the non-negative value of x without regard to its sign. Namely, |x| = x for a positive x, |x| = −x for a negative x (in which case −x is positive), and |0| = 0. For example, the absolute value of 3 is 3, and the absolute value of −3 is also 3. The absolute value of a number may be thought of as its distance from zero.Generalisations of the absolute value for real numbers occur in a wide variety of mathematical settings. For example an absolute value is also defined for the complex numbers, the quaternions, ordered rings, fields and vector spaces. The absolute value is closely related to the notions of magnitude, distance, and norm in various mathematical and physical contexts.";
		String a2 = "This article is about linear algebra and analysis. For field theory, see Field norm. For ideals, see Ideal norm. For group theory, see Norm (group). For norms in descriptive set theory, see prewellordering.In linear algebra, functional analysis and related areas of mathematics, a norm is a function that assigns a strictly positive length or size to each vector in a vector space, other than the zero vector (which has zero length assigned to it). A seminorm, on the other hand, is allowed to assign zero length to some non-zero vectors (in addition to the zero vector).A norm must also satisfy certain properties pertaining to scalability and additivity which are given in the formal definition below.A simple example is the 2-dimensional Euclidean space R2 equipped with the Euclidean norm. Elements in this vector space (e.g., (3, 7)) are usually drawn as arrows in a 2-dimensional cartesian coordinate system starting at the origin (0, 0). The Euclidean norm assigns to each vector the length of its arrow. Because of this, the Euclidean norm is often known as the magnitude.A vector space on which a norm is defined is called a normed vector space. Similarly, a vector space with a seminorm is called a seminormed vector space. It is often possible to supply a norm for a given vector space in more than one way.";

		String a3 = "This article is about linear algebra and analysis. For field";
		// findCosineSimilarity(a1,a2);
		// System.out.println(findCosineSimilarity(a1,a2));
		System.out.println(findIFDF(a2, "This"));
	}

	static double findCosineSimilarity(String firstString, String secondString) {
		Map<String, Integer> firstMap = wordFrequencyMap(firstString);
		Map<String, Integer> secondMap = wordFrequencyMap(secondString);

		Set<String> firstSet = firstMap.keySet();
		Set<String> secondSet = secondMap.keySet();

		Set<String> newSet = new HashSet<String>(firstMap.keySet());

		newSet.retainAll(secondSet); // now new set will have intersection of
										// all the sets

		// numerator here we will have to use common
		Integer numeratorVal = 0;
		Iterator<String> it = newSet.iterator();
		while (it.hasNext()) {
			String val = it.next();
			numeratorVal = numeratorVal + firstMap.get(val)
					* secondMap.get(val);
		}

		// denominator
		Iterator<String> itden1 = firstSet.iterator();
		Iterator<String> itden2 = secondSet.iterator();
		Double denom1 = 0.0D;
		Double denom2 = 0.0D;
		while (itden1.hasNext()) {
			String denVal = itden1.next();

			denom1 = (denom1 + (Math.pow(firstMap.get(denVal), 2)));
		}

		while (itden2.hasNext()) {
			String denVal = itden2.next();
			denom2 = (denom2 + (Math.pow(secondMap.get(denVal), 2)));
		}

		double cosineSimilarity = numeratorVal
				/ (Math.sqrt(denom1) * Math.sqrt(denom2));
		return cosineSimilarity;
	}

	public static double findIFDF(String document, String word) {
		Integer totalWords = 0;
		Map<String, Integer> map = wordFrequencyMap(document);
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		// total number of words present
		while (it.hasNext()) {
			String val = it.next();

			totalWords = totalWords + map.get(val);
		}
		double tf = 0.0D;
		double idf = 0.0D;
		if (map.get(word) != null) {
			tf = (float) map.get(word) / totalWords;
			idf = Math.log(totalWords / map.get(word));
		} else {
			tf = 0.0;
			return 0.0;
		}

		return tf*idf;
	}

	private static Map<String, Integer> wordFrequencyMap(String str) {
		List<String> wordsList = Arrays.asList(str.split("\\s"));
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		Iterator<String> wit = wordsList.iterator();
		while (wit.hasNext()) {
			String val = wit.next();
			if (resultMap.containsKey(val)) {
				Integer oldval = resultMap.get(val);
				oldval++;
				resultMap.put(val, oldval);
			} else {
				resultMap.put(val, 1);
			}
		}
		return resultMap;
	}
}
