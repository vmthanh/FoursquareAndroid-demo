Mô tả ứng dụng:
Ứng dụng được thiết kế dạng tab gồm 2 tab: Map và List.
	-Tab Map: chỉ ra vị trí của bạn hiện thời và các vị trị xung quanh. Thông tin về các vị trí xung quanh được lấy về bằng cách gọi lên Foursquare bằng api: https://api.foursquare.com/v2/venues/search. Vị trí hiện thời được cập nhật cứ mỗi 60s, ứng dụng sẽ kiểm tra vị trí hiện thời và vị trí cũ, nếu vị trí mới nằm ở vị trí khác thì ứng dụng sẽ cập nhật lại vị trí hiện thời và lại gọi lên Foursquare để lấy danh sách các vị trí xung quanh mới.
	- Tab List: thông tin chi tiết của các vị trí xung quanh, bao gồm các thông tin sau: Tên địa điểm, địa chỉ, loại địa điểm và khoảng cách. Cứ mỗi khi danh sách thay đổi, danh sách sẽ được cập nhật lại trong tab List.
Mô tả các function quan trọng:
	- Ứng dụng sử dụng 1 hàm AsyncTask để tránh việc block UI, cứ mỗi 60s sẽ gọi lên Server lấy database về.
	- Sử dụng http client và http response gọi lên Server thông qua API và get db về, sau đó parse thông tin để lấy các thông tin cần thiết.
	- Sử dụng Google Map Api để xác định vị trí hiện thời và vẽ các markers lên bản đồ.
Các hạn chế trong ứng dụng:
	- Vì lý do thời gian nên ứng dụng chỉ thuần sử dụng hàm API https://api.foursquare.com/v2/venues/search để lấy các thông tin trên Foursquare về. Theo thông tin của Foursquare, API này là api public nên không thể trả về được các hình ảnh kèm theo của các vị trí. Nếu muốn lấy về được các hình ảnh, cần phải sử dụng cơ chế Authenticated users, cài đặt thêm libary của Foursquare vào ứng dụng Android. 
Yêu cầu sử dụng:
	- Ứng dụng yêu cầu phãii bật Internet và GPS trong quá trình sử dụng. 


