package com.fengwk.support.dev.yapi;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fengwk
 */
public class SourceReader {

	private Class<?> clazz;
	
	private List<Line> lines = new ArrayList<>();
	
	public SourceReader(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public void init() {
		URL url = clazz.getResource(clazz.getSimpleName() + ".class");
		File f = new File(url.getFile().replace("target/classes", "src/main/java").replace(".class", ".java"));
		if (!f.exists()) {
			f = new File(url.getFile().replace("target/test-classes", "src/test/java").replace(".class", ".java"));
		}
		try (BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
			String line;
			int inClass = 0;
			boolean inComment = false;
			while ((line = r.readLine()) != null) {
				if (inClass == 0 && line.indexOf("class") != -1)
					inClass++;
				if (inClass == 1 && line.indexOf('{') != -1)
					inClass++;
				if (inClass < 2)
					continue;
				if (line.indexOf("/**") != -1)
					inComment = true;
				else if (line.indexOf("*/") != -1)
					inComment = false;
				else
					lines.add(new Line(inComment, line));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<String> findComment(String fieldName) {
		int fi = 0;
		boolean isf = false;
		for (; fi < lines.size(); fi++) {
			Line line = lines.get(fi);
			if (!line.isComment) {
				if (!isf && line.content.indexOf(clazz.getSimpleName()) != -1)
					isf = true;
				if (isf && line.content.indexOf(" " + fieldName + ";") != -1)
					break;
			}
		}
		LinkedList<String> stack = new LinkedList<>();
		fi--;
		boolean in = false;
		for (; fi >= 0; fi--) {
			Line line = lines.get(fi);
			if (line.isComment) {
				in = true;
				stack.push(line.content);
			} else if (in) {
				break;
			}
		}
		List<String> lcs = new ArrayList<>();
		while (!stack.isEmpty()) {
			String lc = stack.pop();
			lc = lc.trim();
			while (lc.length() > 0 && lc.charAt(0) == '*') {
				lc = lc.substring(1, lc.length());
			}
			lc = lc.trim();
			lcs.add(lc);
		}
		return lcs;
	}
	
	class Line {
		boolean isComment;
		String content;
		public Line(boolean isComment, String content) {
			this.isComment = isComment;
			this.content = content;
		}
	}
	
}
