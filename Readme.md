| Layer             | Giải quyết                 |
| ----------------- | -------------------------- |
| Redis Lock        | giảm request tranh chấp    |
| Transaction       | đảm bảo atomic             |
| PESSIMISTIC_WRITE | khóa row                   |
| Unique Constraint | chặn dữ liệu sai cuối cùng |
| @Version          | phát hiện update conflict  |
