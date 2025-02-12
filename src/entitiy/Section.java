package entitiy;

import entity.enums.Role;
import entity.enums.SectionState;

import java.util.List;
import java.util.UUID;

public class Section {
    private String id = UUID.randomUUID().toString();
    private String name;
    private List<Book> books;
    private SectionState status = SectionState.ENABLED;


    public Section() {
    }

    public Section(String id, String name, List<Book> books, SectionState status) {
        this.id = id;
        this.name = name;
        this.books = books;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public SectionState getStatus() {
        return status;
    }

    public void setStatus(SectionState status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return String.format("📚 Section: [%s] %s | 📖 Books: %d | Status: %s",
                id, name, books.size(), status);
    }

    public void showSectionsUI(List<Section> sections, User user) {
        if (sections.isEmpty()) {
            System.out.println("❌ No sections available!");
            return;
        }

        System.out.println("\n========== 📚 Sections List 📚 ==========");

        for (Section section : sections) {
            if (user.getRole().equals(Role.USER) && section.getStatus().equals(SectionState.ENABLED)) {
                System.out.printf("🏷️ [%s] %s (📖 Books: %d)\n",
                        section.getId(), section.getName(), section.getBooks().size());
            } else if (!user.getRole().equals(Role.USER)) {
                System.out.printf("🏷️ [%s] %s (📖 Books: %d, 🔄 Status: %s)\n",
                        section.getId(), section.getName(), section.getBooks().size(), section.getStatus());
            }
        }

        System.out.println("\n=====================================");
    }



}


