import os
from PIL import Image
from pytube import YouTube
import yt_dlp
from moviepy.editor import VideoFileClip, CompositeVideoClip
from moviepy.video.fx.all import crop, resize
import moviepy.editor as mpy
import subprocess
import argparse
import time

if hasattr(Image, 'Resampling'):
    Image.ANTIALIAS = Image.Resampling.LANCZOS

def remove_green_screen(clip):
    # Color #64ec14 in RGB: [100, 236, 20]
    return clip.fx(mpy.vfx.mask_color, color=[100, 236, 20], thr=160, s=30)

def download_video_from_youtube(url, start_time, end_time):
    temp_video_name = f"{url}_{start_time}_{end_time}_temp"
    output_video_name = f"{url}_{start_time}+{end_time}_output"
    
    ydl_opts = {
        'format': 'bestvideo[height<=720]+bestaudio/best[height<=720]',  
        'outtmpl': f'{temp_video_name}.%(ext)s',
        'noplaylist': True,
        'merge_output_format': 'mp4'  # 
    }
    
    with yt_dlp.YoutubeDL(ydl_opts) as ydl:
        ydl.download([url])
    
    input_file = f'{temp_video_name}.mp4'
    output_file = f'{output_video_name}.mp4'
    
    subprocess.run([
        'ffmpeg', '-i', input_file, '-ss', str(start_time), '-to', str(end_time),
        '-c:v', 'libx264', '-c:a', 'aac', '-strict', 'experimental', output_file
    ])

    video_clip = VideoFileClip(output_file)
    
    if os.path.exists(input_file):
        os.remove(input_file)
    
    return video_clip

def main(video1_id, video2_id, video_start, video_end, output_name, is_overlay):
    output_width = 1080
    output_height = 1920
    video1 = download_video_from_youtube(video1_id, video_start, video_end)
    video2 = download_video_from_youtube(video2_id, video_start, video_end)
    overlay_video = VideoFileClip("overlay.mp4")

    video1_resized = video1.resize(width=output_width)

    max_height_video1 = output_height // 2

    if video1_resized.size[1] > max_height_video1:
        video1_resized = video1_resized.resize(height=max_height_video1)

    remaining_height = output_height - video1_resized.size[1]

    video2_resized = video2.resize(height=remaining_height)
    video2_cropped = video2_resized.crop(x_center=video2_resized.w // 2, width=output_width)

    video1_position = (0, 0)
    video2_position = (0, video1_resized.size[1])

    combined_video = CompositeVideoClip([ 
        video1_resized.set_position(video1_position),
        video2_cropped.set_position(video2_position)
    ], size=(output_width, output_height))

    if is_overlay:
        overlay_no_bg = remove_green_screen(overlay_video)
        overlay_cropped = overlay_no_bg.subclip(0, (video_end - video_start))
        overlay_resized = overlay_cropped.resize(height=int(output_height * 0.2))
        final_video = CompositeVideoClip([ 
            combined_video,
            overlay_resized.set_position(("left", "bottom"))
        ], size=(output_width, output_height))
        final_video_with_audio = final_video.set_audio(video1.audio)  
        final_video_with_audio.write_videofile(f"{output_name}.mp4", codec="libx264", fps=30)
        f
    else:
        final_video_with_audio = combined_video.set_audio(video1.audio)  
        final_video_with_audio.write_videofile(f"{output_name}.mp4", codec="libx264", fps=30)
    
    final_video_with_audio.close()
    video2_resized.close()

    if os.path.exists(video1.filename):
        os.remove(video1.filename)
    if os.path.exists(video2.filename):
        os.remove(video2.filename)

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Video editor script")
    
    parser.add_argument("url1", type=str)
    parser.add_argument("url2", type=str)
    parser.add_argument("start_time", type=int)
    parser.add_argument("end_time", type=int)
    parser.add_argument("output_filename", type=str)
    parser.add_argument("is_overlay", type=lambda x: x.lower() == 'true')

    args = parser.parse_args()

    main(args.url1, args.url2, args.start_time, args.end_time, args.output_filename, args.is_overlay)


     

