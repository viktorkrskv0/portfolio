from django.urls import path
from main_app import views

app_name = 'main_app'

urlpatterns = [
    path('',views.home,name='home'),
    path('base/',views.base,name='base'),
    path('about/',views.about,name='about') ,
    path('audio/',views.audio,name='audio') ,
    path('video/',views.video,name='video') ,
    path('contact/',views.contact,name='contact') ,
]
