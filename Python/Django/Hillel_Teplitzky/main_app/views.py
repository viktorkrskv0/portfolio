from django.shortcuts import render
from django.http import HttpResponse


def home(request):
    front_dict = {"back_server_here":"Hello, this text was injected dynamically through the backend"}
    return render(request,'main_app/home.html',context=front_dict)

def base(request):
    return render(request,'main_app/base.html',context={})

def about(request):
    return render(request,'main_app/about.html',context={})

def audio(request):
    return render(request,'main_app/audio.html',context={})

def video(request):
    return render(request,'main_app/video.html',context={})

def contact(request):
    return render(request,'main_app/contact.html',context={})
