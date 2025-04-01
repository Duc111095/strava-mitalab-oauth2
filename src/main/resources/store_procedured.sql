CREATE PROCEDURE `mitalab_app`.`summary_activities`(
	IN start_date datetime(6),
	IN end_date datetime(6),
	IN type_summary char(1)  -- loai summary 
)
BEGIN
	SET type_summary = IFNULL(type_summary, 0);
	drop temporary table if exists report_summary;
	CREATE TEMPORARY TABLE report_summary (
		athlete_id int, 
		athlete_name nvarchar(4000),
		total_distance double,
		total_moving_time int,
		total_elapsed_time int,
		paced double
	);
	insert into report_summary 
	select a.athlete_id, concat(ltrim(b.first_name), " ",ltrim(b.last_name)), sum(a.distance) / 1000, sum(a.moving_time), sum(a.elapsed_time), 0 
	from strava_activity a join strava_user b on a.athlete_id = b.id where a.start_date_local >= start_date and a.start_date_local <= end_date group by a.athlete_id, b.first_name, b.last_name;
	update report_summary set paced = (total_elapsed_time)/(total_distance);
	select * from report_summary;
END