package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao {
    // 1. 新增文章
    public void add(Article article) {
        Connection connection = DBUtil.getConnection();
        String sql = "insert into article values (null, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, article.getTitle());
            statement.setString(2, article.getContent());
            statement.setInt(3, article.getUserId());
            int ret = statement.executeUpdate();
            if (ret != 0) {
                System.out.println("发布文章成功");
                return;
            }
            System.out.println("发布文章失败");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
    }



    // 2. 查看文章列表(把所有的文章信息都查出来(没必要查正文))
    public List<Article> selectAll() {
        List<Article> articles = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        String sql = "select articleId, title, userId from article";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            // 4. 遍历结果集
            while (resultSet.next()) {
                Article article = new Article();
                article.setArticleId(resultSet.getInt("articleId"));
                article.setTitle(resultSet.getString("title"));
                article.setUserId(resultSet.getInt("userId"));
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }


    // 3. 查看指定文章详情. (需要查正文)
    public Article selectById(int articleId) {
        Connection connection = DBUtil.getConnection();
        String sql = "select * from article where articleId = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, articleId);
            resultSet = statement.executeQuery();
            //    由于 id 是主键. 不会重复. 预期最多只能查出一条记录.
            if (resultSet.next()) {
                Article article = new Article();
                article.setArticleId(resultSet.getInt("articleId"));
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
                article.setUserId(resultSet.getInt("userId"));
                return article;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    // 4. 删除指定文章(给定文章 id 来删除)
    public void delete(int articleId) {
        Connection connection = DBUtil.getConnection();
        String sql = "delete from article where articleId = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, articleId);
            int ret = statement.executeUpdate();
            if (ret == 0) {
                System.out.println("删除文章失败");
                return;
            }
            System.out.println("删除文章成功");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
    }
    public static void main(String[] args) {
        ArticleDao articleDao = new ArticleDao();
        // 1. 测试新增文章
//        Article article = new Article();
//        article.setTitle("新时代大学生");
//        article.setContent("新时代大学生应该持续学习新时代大学生应该持续学习新时代大学生应该持续学习新时代大学生应该持续学习");
//        article.setUserId(3);
//        articleDao.add(article);
        // 2. 测试查看文章列表
//        List<Article> articles = articleDao.selectAll();
//        System.out.println(articles);
        // 3. 查看指定文章内容
//        Article article = articleDao.selectById(6);
//        System.out.println(article);
        // 4. 删除文章
//       articleDao.delete(6);
    }

}

