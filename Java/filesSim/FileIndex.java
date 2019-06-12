package il.ac.tau.cs.sw1.ex8.filesSim;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;

public class FileIndex {
	private static final String ERROR = "[ERROR] ";

	public HashMap<File, IHistogram<String>> index = new HashMap<>();

	/**
	 * Given a path to a folder, reads all the files in it and indexes them
	 */
	public void index(String folderPath) {
		// first, clear the previous contents of the index
		clearPreviousIndex();
		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				String path = file.getAbsolutePath();
				System.out.println("Indexing " + path);
				try {
					// add to the index if read is successful
					addFileToIndex(file);
				} catch (IOException e) {
					System.out.println(ERROR + "failed to read from " + path);
				}
			}
		}
	}

	/**
	 * Adds the input file to the index
	 */
	public void addFileToIndex(File file) throws IOException {
		List<String> tokens = FileUtils.readAllTokens(file);
		String path = file.getAbsolutePath();
		if (tokens.isEmpty()) {
			System.out.println(ERROR + "ignoring empty file " + path);
			return;
		}
		IHistogram<String> h = HistogramsFactory.getHistogram();
		h.addAll(tokens);
		this.index.put(file, h);
	}

	/**
	 * Called at the beginning of index() in order to clear the fields from
	 * previously indexed files. After calling it the index contains no files.
	 */
	public void clearPreviousIndex() {
		this.index.clear();
	}

	/**
	 * Given indexed input files, compute their cosine similarity based on their
	 * indexed tokens
	 */
	public double getCosineSimilarity(File file1, File file2) {
		if (!verifyFile(file1) || !verifyFile(file2)) {
			return Double.NaN;
		}

		double scalarProduct = 0;
		double norma1 = 0;
		IHistogram<String> h1 = this.index.get(file1);
		IHistogram<String> h2 = this.index.get(file2);
		Iterator<String> iter1 = h1.iterator();
		while (iter1.hasNext()) {
			String str = iter1.next();
			norma1 = norma1 + Math.pow(h1.getCountForItem(str), 2);
			if (h2.getCountForItem(str) != 0) {
				scalarProduct = scalarProduct + h2.getCountForItem(str) * h1.getCountForItem(str);
			}
		}
		norma1 = Math.sqrt(norma1);

		double norma2 = 0;
		Iterator<String> iter2 = h2.iterator();
		while (iter2.hasNext()) {
			String str = iter2.next();
			norma2 = norma2 + Math.pow(h2.getCountForItem(str), 2);
		}
		norma2 = Math.sqrt(norma2);
		
		return scalarProduct/(norma1*norma2);
	}

	/**
	 * Given indexed input files, return the number of the common token in both
	 * files
	 */
	public int getCommonTokensNum(File file1, File file2) {
		if (!verifyFile(file1) || !verifyFile(file2)) {
			return 0;
		}

		IHistogram<String> h1 = this.index.get(file1);
		IHistogram<String> h2 = this.index.get(file2);
		Iterator<String> iter = h1.iterator();
		int counter = 0;
		while (iter.hasNext()) {
			String str = iter.next();
			if (h2.getCountForItem(str) != 0) {
				counter++;
			}
		}
		return counter;
	}

	/**
	 * returns true iff the input file is currently indexed. Otherwise, prints
	 * an error message.
	 */
	public boolean verifyFile(File file) {
		if (this.index.containsKey(file)) {
			return true;
		} else {
			System.out.println(ERROR + "file is not indexed");
			return false;
		}
	}

	/**
	 * @return the number of files currently indexed.
	 */
	public int getNumIndexedFiles() {
		return this.index.size();
	}

}
