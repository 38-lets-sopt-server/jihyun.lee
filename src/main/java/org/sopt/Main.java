package org.sopt;

import org.sopt.controller.PostController;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.ApiResponse;
import org.sopt.dto.response.PostResponse;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 클라이언트는 Controller만 알면 돼요. Service도 Repository도 몰라도 돼요.
        PostController postController = new PostController();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== 에브리타임 게시판 ===");
            System.out.println("1. 게시글 작성");
            System.out.println("2. 전체 조회");
            System.out.println("3. 단건 조회");
            System.out.println("4. 게시글 수정");
            System.out.println("5. 게시글 삭제");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("제목: ");
                    String title = scanner.nextLine();
                    System.out.print("내용: ");
                    String content = scanner.nextLine();
                    System.out.print("작성자: ");
                    String author = scanner.nextLine();
                    ApiResponse<Void> createResponse = postController.createPost(
                            new CreatePostRequest(title, content, author)
                    );
                    System.out.println(createResponse.getMessage());
                    break;

                case 2:
                    ApiResponse<List<PostResponse>> allResponse = postController.getAllPosts();
                    if (allResponse.isSuccess()) {
                        List<PostResponse> posts = allResponse.getData();
                        if (posts.isEmpty()) {
                            System.out.println("💬 등록된 게시글이 없습니다.");
                        } else {
                            posts.forEach(p -> System.out.println(p + "\n---"));
                        }
                    } else {
                        System.out.println(allResponse.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("조회할 게시글 ID: ");
                    ApiResponse<PostResponse> getResponse = postController.getPost(scanner.nextLong());
                    scanner.nextLine();
                    if (getResponse.isSuccess()) {
                        System.out.println(getResponse.getData());
                    } else {
                        System.out.println(getResponse.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("수정할 게시글 ID: ");
                    Long updateId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("새 제목: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("새 내용: ");
                    String newContent = scanner.nextLine();
                    ApiResponse<Void> updateResponse = postController.updatePost(new UpdatePostRequest(updateId, newTitle, newContent));
                    System.out.println(updateResponse.getMessage());
                    break;

                case 5:
                    System.out.print("삭제할 게시글 ID: ");
                    ApiResponse<Void> deleteResponse = postController.deletePost(scanner.nextLong());
                    scanner.nextLine();
                    System.out.println(deleteResponse.getMessage());
                    break;

                case 0:
                    running = false;
                    System.out.println("👋 프로그램 종료");
                    break;
                default:
                    System.out.println("❗ 잘못된 입력입니다.");
            }
        }
        scanner.close();
    }
}
