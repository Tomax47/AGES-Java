package org.AGES.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class FileInfo {
    private long id;
    private String originalFileName;
    private String storageFileName;
    private long size;
    private String type;
    private Long userId;
    private Long productId;
    private String content;
}
