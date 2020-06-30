//package de.tobiassassin.ADS.Uebungsblatt10.abgabe2; //gitignore -> nur für Eclipse

//package de.hsrm.ads; //für subato

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Trie {
	static class TrieNode {
		SearchTree st = new SearchTree();
		TrieNode maxChild;
		long maxChildCount;
		char value;

		public TrieNode() {
		}

		public TrieNode(char c) {
			this.value = c;
		}

		/* Fügt den Buchstaben c in den Suchbaum des TrieNodes ein. */
		public void add(char c, int count) {
			st.inc(c, count);
			if (st.getCount(c) > maxChildCount) {
				maxChildCount = st.getCount(c);
				maxChild = st.getNode(c);
			}
		}

		/* Liefert den zu c gehörigen TrieNode zurück */
		public TrieNode get(char c) {
			return st.getNode(c);
		}
	}

	public static class SearchTree {
		Node root;

		class Node {
			TrieNode trieNode;
			Node left;
			Node right;
			char key;
			long count;

			public Node(char key, long count) {
				this.key = key;
				this.count = count;
				this.trieNode = new TrieNode(key);
			}
		}

		public SearchTree() {
			root = null;
		}

		/* Erhöht den Zähler des Schlüssels um count. Falls der Schlüssel noch nicht vorhanden ist, wird 
		ein neuer Knoten mit Zähler count eingeführt. */
		public void inc(char key, int count) {
			if (root == null) {
				root = new Node(key, count);
				return;
			}
			Node current = root;
			while (current.key != key) {
				if (key < current.key) {
					if (current.left == null) {
						current.left = new Node(key, count);
						return;
					}
					current = current.left;
				} else {
					if (current.right == null) {
						current.right = new Node(key, count);
						return;
					}
					current = current.right;
				}
			}
			current.count += count;
		}

		/* Liefert den Zähler des Schlüssels key zurück */
		public long getCount(char key) {
			if (root == null)
				return 0L;

			// es wird entweder gefunden oder 0 zurueckgegeben
			var tempN = root;
			while (tempN.key != key) {
				tempN = (key < tempN.key) ? tempN.left : tempN.right;
				if (tempN == null)
					return 0L;
			}
			return tempN.count;
		}

		/* Liefert das (TrieNode-)Kind für key zurück */
		public TrieNode getNode(char key) {
			if (root == null)
				return null;
			Node current = root;
			// es wird entweder gefunden oder NULL geworfen
			while (current.key != key) {
				if (key < current.key) {
					current = current.left;
				} else {
					current = current.right;
				}
				if (current == null)
					return null;
			}
			return current.trieNode;
		}
	}

	// Trie-Klasse
	TrieNode root;

	public Trie() {
		root = new TrieNode('*');
	}

	/* Fügt einen Trainingsquery zum Trie hinzu */
	public void add(String s, int count) {
		TrieNode current = root;
		for (char c : s.toCharArray()) {
			current.add(c, count);
			current = current.get(c);
		}
		current.add('*', count);
	}

	/* Sagt für den Präfix eines Queries den vollständigen Queries voraus. Ist ein Präfix nicht im Baum 
	vorhanden, wird null zurückgegeben. */
	public String predict(String prefix) {
		StringBuilder result = new StringBuilder();
		TrieNode current = root;
		for (int i = 0; i < prefix.length(); i++) {
			if (current.get(prefix.charAt(i)) == null) {
				return null;
			}
			current = current.get(prefix.charAt(i));
			result.append(current.value);
		}
		while (current.maxChild != null && current.maxChild.value != '*') {
			current = current.maxChild;
			result.append(current.value);
		}
		return result.toString();
	}

	public void eval() throws IOException {
		FileReader fr = new FileReader(
			//	new File("").getAbsolutePath() + "/src/main/java/blatt_10/aufgabe1/keyphrases.txt");
							new File("").getAbsolutePath() + "/keyphrases.txt");
		int i;
		StringBuilder buffer = new StringBuilder();
		String string = "";
		while ((i = fr.read()) != -1) {
			if (i == 59) {
				string = buffer.toString();
				buffer = new StringBuilder();
				continue;
			}
			if (i == 10) {
				this.add(string, Integer.parseInt(buffer.toString()));
				buffer = new StringBuilder();
				continue;
			}
			buffer.append((char) i);
		}
		fr.close();
	}

	public static void main(String[] args) throws IOException {
		
		Trie t = new Trie();
		t.eval();
		System.out.println("trump : " + t.predict("trump"));
		System.out.println("german : " + t.predict("german"));
		System.out.println("mo : " + t.predict("mo"));
		System.out.println("paw : " + t.predict("paw"));
		System.out.println("secret : " + t.predict("secret"));
		System.out.println("best : " + t.predict("best"));
		System.out.println("pro : " + t.predict("pro"));
		System.out.println("small : " + t.predict("small"));
		System.out.println("snow : " + t.predict("snow"));
		System.out.println("soc : " + t.predict("soc"));
		TrieNode tn1 = new TrieNode();
		TrieNode tn0 = new TrieNode('*');
		
		/*tn0.add('b', 10);
		tn0.add('u', 10);
		tn0.add('k', 20);
		tn0.add('o', 10);
		tn0.add('v', 30);*/
		Trie t2 = new Trie();
		t2.add("abc", 1);
		t2.add("abd", 1);
		t2.add("aba", 5);
		t2.add("", 10);
		t2.add("abaf", 20);
		String result = t2.predict("abafd");
		System.out.println(result);	
	}
}
