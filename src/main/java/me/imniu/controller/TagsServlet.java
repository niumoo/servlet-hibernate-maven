package me.imniu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import me.imniu.po.Post;
import me.imniu.service.PostService;
import me.imniu.service.TagService;
import me.imniu.utils.ServletUtil;

/**
 * 标签页面 Servlet
 */
@WebServlet("/tags")
public class TagsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 是/tags 
	 * 1：取所有文章 
	 * 2：取所有标签 是/tags?name=xxx 
	 * 1:根据标签名取对应的文章id 
	 * 2：根据文章id取文章
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PostService postService = new PostService();
		TagService tagService = new TagService();
		String tagName = request.getParameter("name");
		if (tagName == null) {
			List<Post> posts = postService.getPostList();
			List<String> tags = tagService.getTagList();
			request.setAttribute("tags", tags);
			request.setAttribute("posts", posts);
			// 页面跳转
			ServletUtil.returnJsp("tags.jsp", request, response);
		}
		if (tagName != null) {

			List<Integer> postIdList = tagService.getPostIdByTagName(tagName);
			ArrayList<Post> posts = new ArrayList<Post>();
			for (Integer id : postIdList) {
				Post post = postService.getPostById(id);
				if (post.getStatus() == 1) {
					posts.add(post);
				}
			}
			  Collections.sort(posts,new SortByUpdate());
			request.setAttribute("tagName", tagName);
			request.setAttribute("posts", posts);
			ServletUtil.returnJsp("tags.jsp", request, response);
		}

	}
	//按更新时间降序
    class SortByUpdate implements Comparator {
        public int compare(Object o1, Object o2) {
         Post s1 = (Post) o1;
         Post s2 = (Post) o2;
         return s2.getUpdateTime().compareTo(s1.getUpdateTime());
       }
    }

}
