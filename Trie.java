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

		/* F�gt den Buchstaben c in den Suchbaum des TrieNodes ein. */ public void add(char c, int count) {
			st.inc(c, count);
			if (st.getCount(c) > maxChildCount) {
				maxChildCount = st.getCount(c);
				maxChild = st.getNode(c);
			}
		}

		/* Liefert den zu c geh�rigen TrieNode zur�ck */ public TrieNode get(char c) {
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

		/* Erh�ht den Z�hler des Schl�ssels um count. Falls der Schl�ssel noch nicht vorhanden ist, wird 
		ein neuer Knoten mit Z�hler count eingef�hrt. */ public void inc(char key, int count) {
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

		/* Liefert den Z�hler des Schl�ssels key zur�ck */ public long getCount(char key) {
			if (root == null)
				return 0L;
			// es wird entweder gefunden oder 0 zurueckgegeben
			Node current = root;
			while (current.key != key) {
				if (key < current.key) {
					current = current.left;
				} else {
					current = current.right;
				}
				if (current == null)
					return 0L;
			}
			return current.count;
		}

		/* Liefert das (TrieNode-)Kind f�r key zur�ck */ public TrieNode getNode(char key) {
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

	/* F�gt einen Trainingsquery zum Trie hinzu */ public void add(String s, int count) {
		TrieNode current = root;
		for (char c : s.toCharArray()) {
			current.add(c, count);
			current = current.get(c);
		}
		current.add('*', count);
	}

	/* Sagt f�r den Pr�fix eines Queries den vollst�ndigen Queries voraus. Ist ein Pr�fix nicht im Baum 
	vorhanden, wird null zur�ckgegeben. */ public String predict(String prefix) {
		StringBuilder result = new StringBuilder();
		TrieNode current = root;
		for (int i = 0; i < prefix.length(); i++) {
			if (current.get(prefix.charAt(i)) == null) {
				return null;
			}
			current = current.get(prefix.charAt(i));
			result.append(current.value);
		}
		// TODO : testTrie2 -> java.lang.NullPointerException at de.hsrm.ads.Trie.predict(Trie.java:148)
		while (current.maxChild.value != '*') {
			current = current.maxChild;
			result.append(current.value);
			if (current.maxChild == null)
				break;
		}
		return result.toString();
	}

	public void eval() throws IOException {
		FileReader fr = new FileReader(
				new File("").getAbsolutePath() + "/src/main/java/blatt_10/aufgabe1/keyphrases.txt");
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
	}

	public static void main(String[] args) throws IOException { /*Trie t = new Trie(); t.eval(); 
																System.out.println("trump : " + t.predict("trump")); System.out.println("german : " + 
																t.predict("german")); System.out.println("mo : " + t.predict("mo")); System.out.println("paw : " 
																+ t.predict("paw")); System.out.println("secret : " + t.predict("secret")); 
																System.out.println("best : " + t.predict("best")); System.out.println("pro : " + 
																t.predict("pro")); System.out.println("small : " + t.predict("small")); System.out.println("snow 
																: " + t.predict("snow"));
																System.out.println("soc : " + t.predict("soc")); TrieNode tn1 = new TrieNode(); TrieNode tn0 = 
																new TrieNode('*'); tn0.add('b', 10); tn0.add('u', 10); tn0.add('k', 20); tn0.add('o', 10); 
																tn0.add('v', 30); Trie t = new Trie(); t.add("abc", 1); t.add("abd", 1); t.add("aba", 5); 
																t.add("abf", 10); t.add("abaf", 20); String result = t.predict("ab"); 
																System.out.println(result);*/
	}

}
